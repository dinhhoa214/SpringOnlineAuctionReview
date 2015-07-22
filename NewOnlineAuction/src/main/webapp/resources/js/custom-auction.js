/**
 *  Spring AJAX 
 */
function getListOfRow() {
			var e = document.getElementById("maxRow");
			var numOfSelect = e.options[e.selectedIndex].value;
			/* window.location
					.replace("/adminUsers?maxRows="+ numOfSelect); */ 
			location.href = "adminUsers?maxRows="+ numOfSelect;
		} 
function getListOfRowBids() {
	var e = document.getElementById("maxRow");
	var numOfSelect = e.options[e.selectedIndex].value;
	/* window.location
			.replace("/adminUsers?maxRows="+ numOfSelect); */ 
	location.href = "adminBids?maxRows="+ numOfSelect;
} 
	function doAjaxPostDelete(id) {
		var idNo = id;
		var $dialog = $('<div></div>').html('Are you sure delete ID = ' + idNo)
				.dialog({
					title : "Delete User ?",
					height : 200,
					width : 350,
					modal : true,
					buttons : [ {
						text : "No",
						icons : {
							primary : "ui-icon-heart"
						},
						click : function() {
							$(this).dialog("close");
						}
					}, {
						text : "Yes",
						icons : {
							primary : "ui-icon-heart"
						},
						click : function() {
							$.ajax({
								type : "POST",
								url : "delete",
								data : "id=" + idNo,
								success : function(response) {
									alert(response);
								}
							});
							$(this).dialog("close");
							document.location.reload(false);
						}
					} ]
				});
	}