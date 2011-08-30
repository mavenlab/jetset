$(function() {
	$('#paymentModeSelect').change(function() {
		if($(this).val() == 'Other Credit Card') {
			$('#ccSelectDiv').show();
		} else {
			$('#ccSelectDiv').hide();
		}
	});
	
	$('#submitBtn').click(submitForm);
});

var submitForm = function() {
	var flag = true;

	$('.errorDiv').removeClass('errorDiv');

	
	var name = $('#nameTF').val();
//	if(name == '') {
//		showError('name', 'Please enter your name');
//		flag = false;
//	}
	
	var nric = $('#nricTF').val();
//	if(nric == '') {
//		showError('nric', 'Please enter your NRIC/Passport No');
//		flag = false;
//	}

	var contact = $('#numberTF').val();
//	if(contact == '') {
//		showError('contact', 'Please enter your Contact Number');
//		flag = false;
//	}
	
	var email = $('#emailTF').val();
//	if(email == '') {
//		showError('email', 'Please enter your email address');
//		flag = false;
//	} else if(!validateEmail(email)) {
//		showError('email', 'Please enter correct email address');
//	}
	
	var receipt = $('#receiptTF').val();
//	if(receipt == '') {
//		showError('receipt', 'Please enter your Receipt');
//		flag = false;
//	}
	
//	if(!$('#subscribeCB').is(':checked')) {
//		showError('subscribe', 'Please check to subscribe');
//		flag = false;
//	}
//
//	if(!$('#agreeCB').is(':checked')) {
//		showError('agree', 'Please check to agree to Shell Terms and Conditions');
//		flag = false;
//	}
	
	var payment = $('#paymentModeSelect').val();
	var cc = $('#ccSelect').val();
	var station = $('#stationSelect').val();
	var grade = $('#gradeSelect').val();
	
	var data = {
			name: name,
			nric: nric,
			number: contact,
			email: email,
			payment: payment,
			cc: cc,
			station: station,
			receipt: receipt,
			grade: grade,
			subscribe:$('#subscribeCB').is(':checked'),
			agree: $('#agreeCB').is(':checked')
	};
	
	if($('#memberYRB').is(':checked')) {
		data.member = true;
	} else if($('#memberNRB').is(':checked')) {
		data.member = false;
	}
	
	$.post('/rest/web_entry/add', data, function(result) {
		if(result.status == 'ok') {
			var entryId = "";
			$.each(result.messages, function(key, val) {
				if(val.name == 'entryId') {
					entryId = val.message;
				}
			});
			
			window.location = "/thankyou/" + entryId;
		} else {
			$.each(result.messages, function(key, val) {
				if(val.name != 'error') {
					showError(val.name, val.message);
				} else {
					alert(val.message);
				}
			});
		}
	});
};

function showError(field, message) {
	$('#' + field + 'Error').text('* ' + message);
	$('#' + field + 'WrapperDiv').addClass('errorDiv');
}

function validateEmail(email) { 
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
	return email.match(re);
}