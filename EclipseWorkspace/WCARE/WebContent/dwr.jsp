<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page isELIgnored="false" %>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/interface/AreaMasterResource.js"> </script>
<script type="text/javascript" src="${ contextPath }/scripts/mscript/lib/jquery-1.10.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DWR Test</title>
<script type="text/javascript">
	$(document).ready(function(){
		
	});
</script>
</head>
<body>
Customer Id: <input type="text" id="stateId">
<input type="button" value="Get Area By State Id" onclick="getAllAreas();">
<!-- public List<IWecMasterVo> getActive(CustomerMasterVo customer){
		return service.getActive(customer);
	}
	
	public List<IWecMasterVo> getActive(EbMasterVo eb){
		return service.getActive(eb);
	} -->
<script type="text/javascript">
	var allAreas = null;

	function getAllAreas(){
		var id = document.getElementById("stateId").value;
		console.log(id);
		if(allAreas == null){
			AreaMasterResource.getAll(
				function getAllAreasHandler(data){
					allAreas = data;
					console.dir(data);
					displayAreasByStateId(allAreas, id);
				});
		}
		else
			displayAreasByStateId(allAreas, id);
	}
	
	function getAllAreasHandler(data){
		allAreas = data;
		console.dir(data);
		displayAreasByStateId(allAreas, document.getElementById("stateId").value);
		
	}
	
	function displayAreasByStateId(areasVo, stateId){
		$.each(areasVo, function (index, areaVo) {
			if(areaVo.state.id == stateId){
				console.log(areaVo.name);
			}
		}); 
	}

	function getStateId(data){
		console.dir(data);
		/* $.each(data, function (index, selectVo) {
			console.log(index + ":" + selectVo.name + ":" + selectVo.id);
			console.log("Eb: " + selectVo.eb.name + ", Site: " + selectVo.eb.site.name + ", Area: " + selectVo.eb.site.area.name + ", State: " + selectVo.eb.site.area.state.name + ", Customer: " + selectVo.customer.name);
		}); */
	}
</script>
</body>
</html>