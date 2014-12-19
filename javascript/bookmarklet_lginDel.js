javascript:( function() {
	var motherFrm = window.frames[0];
	var form1 = motherFrm.document.getElementById('form1');
	var divLGIN = motherFrm.document.getElementById('divLGIN');
	
	if(divLGIN == null) {   return; }
	var kit_commNavi_bar = motherFrm.document.getElementById('kit_commNavi_bar');
	var kit_commNavi_container = motherFrm.document.getElementById('kit_commNavi_container');
	form1.removeChild(divLGIN);
	kit_commNavi_bar.removeAttribute('style');
	kit_commNavi_container.removeAttribute('style');
	alert(motherFrm.document.getElementById('searchWord').style.left);
	})();