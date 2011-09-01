var pagination;
var page = 1;
var msisdn;
var start;
var end;

$(function() {
	
	$(":date").dateinput({ trigger: true, format: 'dd mmmm yyyy' });
	
	$(":date:first").data("dateinput").change(function() {
		// we use it's value for the seconds input min option
		$(":date:last").data("dateinput").setMin(this.getValue(), true);
	});
	
	$('#searchBtn').click(onSearchClicked);
});

var onPageLinkClicked = function(event) {
	event.preventDefault();
	page = $(this).attr('rel');
	var data = {
			page : page,
			msisdn : msisdn,
			start : start,
			end : end
	};
	
	submitSearch(data);
}

var onSearchClicked = function(event) {
	event.preventDefault();
	$.blockUI({ css: { 
		border: 'none', 
		padding: '15px', 
		backgroundColor: '#000', 
		'-webkit-border-radius': '10px', 
		'-moz-border-radius': '10px', 
		opacity: .5,
		color: '#fff' },
		message: 'Searching ...'
	});
	
	var data = {
			page : page,
			msisdn : $('#msisdn').val(),
			start : $('#startDate').val(),
			end : $('#endDate').val()
	};

	submitSearch(data);
};

var submitSearch = function(data) {
	$.get("/rest/manager/entry/list", data, onSearchCompleted);
}

var onSearchCompleted = function(result) {
	if(result.status == 'ok') {
		pagination = result.pagination;
		$('#pagination-flickr').empty();
		
		var prev = '<li class="previous"><a id="prevPage" href="#">&#171; Previous</a></li>';
		if(!pagination.prev) {
			prev = '<li class="previous-off">&#171; Previous</li>';
		}
		$('#pagination-flickr').append(prev);

		$.each(pagination.pageNumbers, function(key, val) {
			if(val == pagination.currentPageNumber) {
				$('#pagination-flickr').append('<li class="active">' + val + '</li>');
			} else {
				$('#pagination-flickr').append('<li><a class="pageLink" href="#" rel="' + val + '">' + val + '</a></li>');
			}
		});
		
		var next = '<li class="next"><a id="nextPage" href="#">Next &#187;</a></li>';
		if(!pagination.next) {
			next = '<li class="next-off">Next &#187</li>';
		}
		$('#pagination-flickr').append(next);
		
		$('a.pageLink').click(onPageLinkClicked);
		
		$('#entryTable').empty();
		
		$('#entryTable').append(
			'<tr>' +
			'	<th class="id">ID</th>' +
			'	<th class="channel">Channel</th>' +
			'	<th>Mobile</th>' +
			'	<th>NRIC/Passport</th>' +
			'	<th>Station</th>' +
			'	<th>Receipt</th>' +
			'	<th>Prize</th>' +
			'	<th>Status</th>' +
			'</tr>'

		);
		
		$.each(result.entries, function(key, entryMap) {
			var entry = entryMap.entry;
			var channel = entryMap.type;
			var prize = "&#160;";
			if(entry.prize != null) {
				prize = entry.prize.name;
			}
			var row = '<tr>' +
					'<td class="alignRight">' + entry.id + '</td>' +
					'<td>' + channel + '</td>' +
					'<td>' + entry.msisdn + '</td>' +
					'<td>' + entry.nric + '</td>' +
					'<td>' + entry.station.name + '</td>' +
					'<td>' + entry.receipt + '</td>' +
					'<td>' + prize + '</td>' +
					'<td>' + entry.status + '</td>' +
					'</tr>';
			
			$('#entryTable').append(row);
		});
		
	} else {
		$.each(result.messages, function(key, val) {
			$('#' + val.name + 'Error').text('* ' + val.message);
			$('#' + val.name + 'Div').addClass('errorDiv');
		});
	}
	msisdn = $('#msisdn').val();
	start = $('#startDate').val();
	end = $('#endDate').val();
	$.unblockUI();
};