<%@taglib uri="../../WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="../../WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page isELIgnored="false" %>
<logic:iterate name="${param.beanIdentifier}" id="MasterData">
	<option value = '<bean:write name="MasterData" property="optionValue"/>'><bean:write name="MasterData" property="textValue"/></option>
</logic:iterate>