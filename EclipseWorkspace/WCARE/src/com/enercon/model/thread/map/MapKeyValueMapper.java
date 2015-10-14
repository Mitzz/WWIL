package com.enercon.model.thread.map;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MapKeyValueMapper<Key, Value> {
	
	//List of Processing Objects to get result and store it in a Map
	public Map<Key, Value> submit(List<MapValueEvaluatorWorker<Key, Value>> worker){
		int noOfWorker = worker.size();
		ExecutorService factory = Executors.newFixedThreadPool(noOfWorker);
		
		Map<Key, Value> allWorkerResult = new LinkedHashMap<Key, Value>();
		Map<Key, Future<Value>> workerResultList = new LinkedHashMap<Key, Future<Value>>();
		
		for(MapValueEvaluatorWorker<Key, Value> w : worker){
			workerResultList.put(w.getKey(), factory.submit(w));
		}
		
		factory.shutdown();
		
		try {
			factory.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		Key key = null;
		Future<Value> result = null;
		for (Map.Entry<Key, Future<Value>> workerResult : workerResultList.entrySet()) {
			key = workerResult.getKey();
			result = workerResult.getValue();
			try {
				allWorkerResult.put(key, result.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return allWorkerResult;
	}
}