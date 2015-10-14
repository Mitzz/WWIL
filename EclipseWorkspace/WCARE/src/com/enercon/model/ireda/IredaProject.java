package com.enercon.model.ireda;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.enercon.ajax.interfaces.AjaxVo;
import com.enercon.dao.IredaDAO;
import com.enercon.dao.WECParameterVoDao;
import com.enercon.model.report.ManyWECsManyDatesTotal;
import com.enercon.model.report.ManyWECsManyDatesWECWiseTotal;
import com.enercon.model.report.ManyWECsOneDateTotal;
import com.enercon.model.report.ManyWECsOneDateWECWiseTotal;
import com.enercon.model.thread.map.ManyWECsManyDatesTotalWorker;
import com.enercon.model.thread.map.ManyWECsOneDateTotalWorker;
import com.enercon.model.thread.map.MapKeyValueMapper;
import com.enercon.model.thread.map.MapValueEvaluatorWorker;

public class IredaProject implements Comparable<IredaProject>, AjaxVo{
	private String projectNo;
	private Integer stateCount;
	private Map<String, String> stateIdNameMapping = new LinkedHashMap<String, String>();
	private Map<String, Set<String>> stateWiseWecIdMapping = new LinkedHashMap<String, Set<String>>();
	private Map<String, String> wecIdNameMapping = new HashMap<String, String>();

	//For One Day
	private Map<String, ManyWECsOneDateTotal> stateWiseTotalForOneDay;
	private ManyWECsOneDateTotal grandTotalForOneDay;
	
	//For Many Days
	private Map<String, ManyWECsManyDatesTotal> stateWiseTotalForManyDays;
	private ManyWECsManyDatesTotal grandTotalForManyDays;
	
	private Map<String, ManyWECsManyDatesWECWiseTotal> stateWiseWECWiseDetailsForManyDays;
	private Map<String, ManyWECsOneDateWECWiseTotal> stateWiseWECWiseDetailsForOneDay;
	
	private WECParameterVoDao wecDao = new WECParameterVoDao();

	public IredaProject(String projectNo) {
		super();
		
		this.projectNo = projectNo;
		
		IredaDAO dao = new IredaDAO();
		String key = "";
		Set<String> wecIdsBasedOnState = null;
		
		try {
			setStateIdNameMapping(dao.getStateIdNameMapping(projectNo));
			this.stateCount = 0;
			for (Map.Entry<String, String> entry : getStateIdNameMapping().entrySet()) {
				
				this.stateCount += 1;
				key = entry.getKey();
				wecIdsBasedOnState = dao.getWecIdsBasedOnStateId(projectNo, key);
				stateWiseWecIdMapping.put(key, wecIdsBasedOnState);
			}
			
			setWecIdNameMapping(dao.getWecIdNameMapping(projectNo));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public IredaProject populateStateWiseTotalDataForOneDay(String date) throws SQLException{
		Map<String, ManyWECsOneDateTotal> stateWiseTotalForOneDay = new LinkedHashMap<String, ManyWECsOneDateTotal>(); 

		String key = "";
		Set<String> value = null;
		for (Map.Entry<String, Set<String>> entry : stateWiseWecIdMapping.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			stateWiseTotalForOneDay.put(key, wecDao.getManyWECsOneDateTotal(value, date));
			
		}

		setStateWiseTotalForOneDay(stateWiseTotalForOneDay);
		return this;
	}
	
	public IredaProject populateStateWiseTotalDataForOneDayWithThreading(String date) throws SQLException{
		
		MapKeyValueMapper<String, ManyWECsOneDateTotal> mapper = new MapKeyValueMapper<String, ManyWECsOneDateTotal>();
		List<MapValueEvaluatorWorker<String, ManyWECsOneDateTotal>> workers = new ArrayList<MapValueEvaluatorWorker<String, ManyWECsOneDateTotal>>();
		
		String stateId = "";
		Set<String> wecIds = null;
		for (Map.Entry<String, Set<String>> entry : stateWiseWecIdMapping.entrySet()) {
			
			stateId = entry.getKey();
			wecIds = entry.getValue();
			//System.out.println("State : " + key + " Wec Size : " + value.size() + " WEC Ids : " + value);
			workers.add(new ManyWECsOneDateTotalWorker(stateId, wecIds, date));
			
		}
		setStateWiseTotalForOneDay(mapper.submit(workers));
		//System.out.println(getStateWiseTotalForOneDay());
		return this;
	}
	public IredaProject populateStateWiseTotalDataForOneDayWithThreadingImprovement(String date) throws SQLException{
		setStateWiseTotalForOneDay(getMappingWiseManyWECsTotalForOneDay(date, stateWiseWecIdMapping));
		return this;
	}
	
	public Map<String, ManyWECsOneDateTotal> getMappingWiseManyWECsTotalForOneDay(String date, Map<String, Set<String>> wecIdsMapping){
		MapKeyValueMapper<String, ManyWECsOneDateTotal> mapper = new MapKeyValueMapper<String, ManyWECsOneDateTotal>();
		List<MapValueEvaluatorWorker<String, ManyWECsOneDateTotal>> workers = new ArrayList<MapValueEvaluatorWorker<String, ManyWECsOneDateTotal>>();
		
		String key = "";
		Set<String> wecIds = null;
		for (Map.Entry<String, Set<String>> entry : wecIdsMapping.entrySet()) {
			key = entry.getKey();
			wecIds = entry.getValue();
			//System.out.println("key : " + key + " Wec Size : " + value.size() + " WEC Ids : " + value);
			workers.add(new ManyWECsOneDateTotalWorker(key, wecIds, date));
			
		}
		return mapper.submit(workers);
	}
	
	public IredaProject populateStateWiseTotalDataForManyDays(String fromDate, String toDate) throws SQLException{
		WECParameterVoDao wecDao = new WECParameterVoDao();
		
		Map<String, ManyWECsManyDatesTotal> stateWiseTotalForManyDays = new LinkedHashMap<String, ManyWECsManyDatesTotal>(); 

		String key = "";
		Set<String> value = null;
		for (Map.Entry<String, Set<String>> entry : stateWiseWecIdMapping.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			//System.out.println("State : " + key + " Wec Size : " + value.size() + " WEC Ids : " + value);
			
			stateWiseTotalForManyDays.put(key, wecDao.getManyWECsManyDatesTotal(value, fromDate, toDate));
			
		}
		//System.out.println(getStateWiseDataForOneDay);
		setStateWiseTotalForManyDays(stateWiseTotalForManyDays);
		return this;
	}
	
	public IredaProject populateStateWiseTotalDataForManyDaysWithThreading(String fromDate, String toDate) throws SQLException{
		MapKeyValueMapper<String, ManyWECsManyDatesTotal> m = new MapKeyValueMapper<String, ManyWECsManyDatesTotal>();
		List<MapValueEvaluatorWorker<String, ManyWECsManyDatesTotal>> w = new ArrayList<MapValueEvaluatorWorker<String, ManyWECsManyDatesTotal>>();
		
		String key = "";
		Set<String> value = null;
		for (Map.Entry<String, Set<String>> entry : stateWiseWecIdMapping.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			w.add(new ManyWECsManyDatesTotalWorker(key, value, fromDate, toDate));
			
		}
		setStateWiseTotalForManyDays(m.submit(w));
		return this;
	}

	public IredaProject populateStateWiseTotalDataForManyDaysWithThreadingImprovement(String fromDate, String toDate) throws SQLException{
		setStateWiseTotalForManyDays(getMappingWiseManyWECsTotalForManyDays(fromDate, toDate, stateWiseWecIdMapping));
		return this;
	}
	
	public Map<String, ManyWECsManyDatesTotal> getMappingWiseManyWECsTotalForManyDays(String fromDate, String toDate, Map<String, Set<String>> stateWiseWecIdsMapping){
		MapKeyValueMapper<String, ManyWECsManyDatesTotal> mapProducer = new MapKeyValueMapper<String, ManyWECsManyDatesTotal>();
		List<MapValueEvaluatorWorker<String, ManyWECsManyDatesTotal>> workers = new ArrayList<MapValueEvaluatorWorker<String, ManyWECsManyDatesTotal>>();
		
		String stateId = "";
		Set<String> wecIds = null;
		
		for (Map.Entry<String, Set<String>> entry : stateWiseWecIdsMapping.entrySet()) {
			stateId = entry.getKey();
			wecIds = entry.getValue();
			workers.add(new ManyWECsManyDatesTotalWorker(stateId, wecIds, fromDate, toDate));
		}
		
		return mapProducer.submit(workers);
	}
	
	public IredaProject populateGrandTotalForOneDay(String date) throws SQLException{
		if(stateCount == 1){
			for (Map.Entry<String, ManyWECsOneDateTotal> entry : getStateWiseTotalForOneDay().entrySet()) {
				grandTotalForOneDay = entry.getValue();
			}
			//System.out.println("Not Calculating Grand Total");
		}
		else{
			Set<String> wecIds = new LinkedHashSet<String>();
			for (Map.Entry<String, Set<String>> entry : stateWiseWecIdMapping.entrySet()) {
				wecIds.addAll(entry.getValue());
			}
			
			setGrandTotalForOneDay(wecDao.getManyWECsOneDateTotal(wecIds, date));
			//System.out.println(grandTotalForOneDay);
		}
		return this;
	}
	
	public IredaProject populateGrandTotalForManyDays(String fromDate, String toDate) throws SQLException{
		if(stateCount == 1){
			for (Map.Entry<String, ManyWECsManyDatesTotal> entry : getStateWiseTotalForManyDays().entrySet()) {
				grandTotalForManyDays = entry.getValue();
			}
			//System.out.println("Not Calculating Grand Total");
		}
		else{
			Set<String> wecIds = new LinkedHashSet<String>();
			for (Map.Entry<String, Set<String>> entry : stateWiseWecIdMapping.entrySet()) {
				wecIds.addAll(entry.getValue());
			}
			
			setGrandTotalForManyDays(wecDao.getManyWECsManyDatesTotal(wecIds, fromDate, toDate));
			//System.out.println(grandTotalForOneDay);
		}
		return this;
	}
	
	public IredaProject populateWecWiseDetailsForManyDaysBasedOnState(String stateId, String fromDate, String toDate) throws SQLException{
		WECParameterVoDao wecDao = new WECParameterVoDao();
		
		Map<String, ManyWECsManyDatesWECWiseTotal> wecWiseDetailsForManyDaysBasedOnState = new LinkedHashMap<String, ManyWECsManyDatesWECWiseTotal>(); 

		if(stateWiseWecIdMapping.containsKey(stateId)){
			wecWiseDetailsForManyDaysBasedOnState.put(stateId, wecDao.getManyWECsManyDatesWECWiseTotal(stateWiseWecIdMapping.get(stateId), fromDate, toDate));
		}
		
		setStateWiseWECWiseDetailsForManyDays(wecWiseDetailsForManyDaysBasedOnState);
		return this;
	}
	
	public Map<String, String> getStateIdNameMapping() {
		return stateIdNameMapping;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public void setStateIdNameMapping(Map<String, String> stateIdNameMapping) {
		this.stateIdNameMapping = stateIdNameMapping;
	}

	public Map<String, String> getWecIdNameMapping() {
		return wecIdNameMapping;
	}

	public void setWecIdNameMapping(Map<String, String> wecIdNameMapping) {
		this.wecIdNameMapping = wecIdNameMapping;
	}

	@Override
	public String toString() {
		return "IredaProject [projectNo=" + projectNo + ", stateIdNameMapping="
				+ stateIdNameMapping + ", stateWiseWecIdMapping="
				+ stateWiseWecIdMapping + ", wecIdNameMapping="
				+ wecIdNameMapping + "]";
	}

	public Map<String, ManyWECsOneDateTotal> getStateWiseTotalForOneDay() {
		return stateWiseTotalForOneDay;
	}

	public void setStateWiseTotalForOneDay(Map<String, ManyWECsOneDateTotal> stateWiseTotalForOneDay) {
		this.stateWiseTotalForOneDay = stateWiseTotalForOneDay;
	}

	public ManyWECsOneDateTotal getGrandTotalForOneDay() {
		return grandTotalForOneDay;
	}

	public void setGrandTotalForOneDay(ManyWECsOneDateTotal grandTotalForOneDay) {
		this.grandTotalForOneDay = grandTotalForOneDay;
	}

	public Map<String, Set<String>> getStateWiseWecIdMapping() {
		return stateWiseWecIdMapping;
	}

	public void setStateWiseWecIdMapping(
			Map<String, Set<String>> stateWiseWecIdMapping) {
		this.stateWiseWecIdMapping = stateWiseWecIdMapping;
	}

	public Integer getStateCount() {
		return stateCount;
	}

	public void setStateCount(Integer stateCount) {
		this.stateCount = stateCount;
	}

	public int compareTo(IredaProject that) {
		//ascending order
		return Integer.parseInt(this.projectNo) - Integer.parseInt(that.projectNo);
 
		//descending order
		//return compareQuantity - Integer.parseInt(this.projectNo);
	}

	public Map<String, ManyWECsManyDatesTotal> getStateWiseTotalForManyDays() {
		return stateWiseTotalForManyDays;
	}

	public void setStateWiseTotalForManyDays(
			Map<String, ManyWECsManyDatesTotal> stateWiseTotalForManyDays) {
		this.stateWiseTotalForManyDays = stateWiseTotalForManyDays;
	}

	public ManyWECsManyDatesTotal getGrandTotalForManyDays() {
		return grandTotalForManyDays;
	}

	public void setGrandTotalForManyDays(ManyWECsManyDatesTotal grandTotalForManyDays) {
		this.grandTotalForManyDays = grandTotalForManyDays;
	}

	public Map<String, ManyWECsOneDateWECWiseTotal> getStateWiseWECWiseDetailsForOneDay() {
		return stateWiseWECWiseDetailsForOneDay;
	}

	public void setStateWiseWECWiseDetailsForOneDay(
			Map<String, ManyWECsOneDateWECWiseTotal> stateWiseWECWiseDetailsForOneDay) {
		this.stateWiseWECWiseDetailsForOneDay = stateWiseWECWiseDetailsForOneDay;
	}

	public Map<String, ManyWECsManyDatesWECWiseTotal> getStateWiseWECWiseDetailsForManyDays() {
		return stateWiseWECWiseDetailsForManyDays;
	}

	public void setStateWiseWECWiseDetailsForManyDays(
			Map<String, ManyWECsManyDatesWECWiseTotal> stateWiseWECWiseDetailsForManyDays) {
		this.stateWiseWECWiseDetailsForManyDays = stateWiseWECWiseDetailsForManyDays;
	}

	
}


//class MapProducer<T> {
//	
//	//List of Processing Objects to get result and store it in a Map
//	public Map<String, T> submit(List<MapProducerWorker<T>> worker){
//		int noOfWorker = worker.size();
//		ExecutorService factory = Executors.newFixedThreadPool(noOfWorker);
//		
//		Map<String, T> allWorkerResult = new LinkedHashMap<String, T>();
//		Map<String, Future<T>> workerResultList = new LinkedHashMap<String, Future<T>>();
//		
//		for(MapProducerWorker<T> w : worker){
//			workerResultList.put(w.getKey(), factory.submit(w));
//		}
//		
//		factory.shutdown();
//		
//		try {
//			factory.awaitTermination(1, TimeUnit.DAYS);
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		}
//		
//		String key = "";
//		Future<T> result = null;
//		for (Map.Entry<String, Future<T>> workerResult : workerResultList.entrySet()) {
//			key = workerResult.getKey();
//			result = workerResult.getValue();
//			try {
//				allWorkerResult.put(key, result.get());
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				e.printStackTrace();
//			}
//		}
//		return allWorkerResult;
//	}
//}

//Processing/Worker Objects - To do some work to produce result
//interface MapProducerWorker<T> extends Callable<T>{
//	public String getKey();
//}
//
//class ManyWECsManyDatesTotalWorker implements MapProducerWorker<ManyWECsManyDatesTotal>{
//	private String stateId;
//	private Set<String> wecIds;
//	private String fromDate;
//	private String toDate;
//	
//	public ManyWECsManyDatesTotalWorker(String stateId, Set<String> wecIds, String fromDate, String toDate){
//		this.stateId = stateId;
//		this.wecIds = wecIds;
//		this.fromDate = fromDate;
//		this.toDate = toDate;
//	}
//	
//	public String getKey(){
//		return stateId;
//	}
//
//	public ManyWECsManyDatesTotal call() throws Exception {
//		WECDao wecDao = new WECDao();
//		return wecDao.getManyWECsManyDatesTotal(wecIds, fromDate, toDate);
//	}
//}
//
//class ManyWECsOneDateTotalWorker implements MapProducerWorker<ManyWECsOneDateTotal>{
//	private String stateId;
//	private Set<String> wecIds;
//	private String date;
//	
//	public ManyWECsOneDateTotalWorker(String stateId, Set<String> wecIds, String date){
//		this.stateId = stateId;
//		this.wecIds = wecIds;
//		this.date = date;
//	}
//	
//	public String getKey(){
//		return stateId;
//	}
//
//	public ManyWECsOneDateTotal call() throws Exception {
//		WECDao wecDao = new WECDao();
//		return wecDao.getManyWECsOneDateTotal(wecIds, date);
//	}
//} 