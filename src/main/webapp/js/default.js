$(function() {
	$(".mainMenu").click(function() {
		var ref = $(this).attr('ref');
		if(ref == 'home') {
			ref = "";
		}
		if(ref != 'prizes') {
			window.location = "/" + ref;
		}
	});
});