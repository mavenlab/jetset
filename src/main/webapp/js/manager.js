var pagination;
var page = 1;
var msisdn;
var start;
var end;
var type = 'ALL';

$(function() {
	$(":date").dateinput({ trigger: true, format: 'dd mmmm yyyy' });
	
	$(":date:first").data("dateinput").change(function() {
		// we use it's value for the seconds input min option
		$(":date:last").data("dateinput").setMin(this.getValue(), true);
	});
	
	$('#searchBtn').click(onSearchClicked);
	$('#downloadBtn').click(onDownloadClicked);
	
	$(".mainMenu").click(function() {
		var ref = $(this).attr('ref');
		if(ref == 'home') {
			ref = "";
		}
		//if(ref != 'prizes') {
			window.location = "/" + ref;
		//}
	});
});

var onPageLinkClicked = function(event) {
	event.preventDefault();
	page = $(this).attr('rel');
	var data = {
			page : page,
			msisdn : msisdn,
			start : start,
			end : end,
			type: type
	};
	submitSearch(data);
};

var onPrevPageClicked = function(event) {
	event.preventDefault();
	page--;
	var data = {
			page : page,
			msisdn : msisdn,
			start : start,
			end : end,
			type: type
	};
	submitSearch(data);
};

var onNextPageClicked = function(event) {
	event.preventDefault();
	page++;
	var data = {
			page : page,
			msisdn : msisdn,
			start : start,
			end : end,
			type: type
	};
	submitSearch(data);
};

var onfirstPageClicked = function(event) {
	event.preventDefault();
	page = 1;
	var data = {
			page : page,
			msisdn : msisdn,
			start : start,
			end : end,
			type: type
	};
	submitSearch(data);
};

var onlastPageClicked = function(event) {
	event.preventDefault();
	page = $(this).attr('rel');
	var data = {
			page : page,
			msisdn : msisdn,
			start : start,
			end : end,
			type: type
	};
	submitSearch(data);
};

var onSearchClicked = function(event) {
	event.preventDefault();
	var data = {
			page : page,
			msisdn : $('#msisdn').val(),
			start : $('#startDate').val(),
			end : $('#endDate').val(),
			type: type
	};
	submitSearch(data);
};

var onDownloadClicked = function(event) {
	event.preventDefault();
	
	msisdn = $('#msisdn').val();
	start = $('#startDate').val();
	end = $('#endDate').val();
	
	$.blockUI({ css: { 
		border: 'none', 
		padding: '15px', 
		backgroundColor: '#000', 
		'-webkit-border-radius': '10px', 
		'-moz-border-radius': '10px', 
		opacity: .5,
		color: '#fff' },
		message: 'Downloading ...'
	});
	
	var servletUrl = '/download/entries?type=' + type + '&start=' + start + '&end=' + end + '&msisdn=' + msisdn;
	window.open (servletUrl,"Download");
	$.unblockUI();
};

var submitSearch = function(data) {
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
	$.get("/rest/manager/entry/list", data, onSearchCompleted);
}

var onSearchCompleted = function(result) {
	if(result.status == 'ok') {
		pagination = result.pagination;
		$('#pagination-flickr').empty();

		if(pagination.next || pagination.prev) {
			var n = Math.floor(pagination.currentPageNumber / 10);
			var nLast = Math.floor(pagination.lastPageNumber / 10);
			
			var first = '<li class="previous"><a id="firstPage" href="#">&#171; First</a></li>';
			var prev = '<li class="previous"><a id="prevPage" href="#">&#171; Previous</a></li>';
			if(!pagination.prev) {
				first = '<li class="previous-off">&#171; First</li>';
				prev = '<li class="previous-off">&#171; Previous</li>';
			}
			$('#pagination-flickr').append(first);
			$('#pagination-flickr').append(prev);
			if(n > 0) {
				$('#pagination-flickr').append('<li class="elp">...</li>');
			}

			$.each(pagination.pageNumbers, function(key, val) {
				var nPage = Math.floor(val / 10);
				if(val == pagination.currentPageNumber) {
					$('#pagination-flickr').append('<li class="active">' + val + '</li>');
				} else if(nPage == n) {
					$('#pagination-flickr').append('<li><a class="pageLink" href="#" rel="' + val + '">' + val + '</a></li>');
				}
			});
			
			var next = '<li class="next"><a id="nextPage" href="#">Next &#187;</a></li>';
			var last = '<li class="next"><a id="lastPage" rel="' + pagination.lastPageNumber + '" href="#">Last &#187;</a></li>';
			if(!pagination.next) {
				next = '<li class="next-off">Next &#187</li>';
				last = '<li class="next-off">Last &#187</li>';
			}
			if(n < nLast) {
				$('#pagination-flickr').append('<li class="elp">...</li>');
			}
			$('#pagination-flickr').append(next);
			$('#pagination-flickr').append(last);
			
			$('a.pageLink').click(onPageLinkClicked);
			$('a#prevPage').click(onPrevPageClicked);
			$('a#nextPage').click(onNextPageClicked);
			$('a#firstPage').click(onfirstPageClicked);
			$('a#lastPage').click(onlastPageClicked);
		}
		
		$('#entryTable').empty();
		
		$('#entryTable').append(
			'<tr>' +
			'	<th class="id">ID</th>' +
			'	<th class="channel">Channel</th>' +
			'	<th>Mobile</th>' +
			'	<th>NRIC/Passport</th>' +
			'	<th>Station</th>' +
			'	<th class="receipt">Receipt</th>' +
			'	<th>Prize</th>' +
			'	<th class="createdAt">Date</th>' +
			'	<th>Status</th>' +
			'</tr>'
		);
//			'	<th>Action</th>' +
		
		$.each(result.entries, function(key, entryMap) {
			var entry = entryMap.entry;
			var channel = entryMap.type;
			var prize = "&#160;";
			var station = "&#160;"
			if(entry.prize != null) {
				prize = entry.prize.name;
			}
			if(entry.station != null) {
				station = entry.station.name + " (" + entry.station.id + ")";
			}
			var createdAt = new Date(entry.createdAt);
			var row = '<tr class="entryRow" rel="' + entry.id + '" href="/tnc">' +
					'<td class="alignRight">' + entry.id + '</td>' +
					'<td>' + channel + '</td>' +
					'<td>' + entry.msisdn + '</td>' +
					'<td>' + entry.nric + '</td>' +
					'<td>' + station + '</td>' +
					'<td>' + entry.receipt + '</td>' +
					'<td>' + prize + '</td>' +
					'<td>' + $.format.date(createdAt.toString(), "dd MMMM yyyy HH:mm") + '</td>' +
					'<td>' + entry.status + '</td>' +
					'</tr>';
//					'<td><a class="viewEntry" rel="#overlay" href="/tnc">view</a></td>' +
			
			$('#entryTable').append(row);
		});
		
		$("a.viewEntry").overlay({

			mask: '#555',
			effect: 'apple',

			onBeforeLoad: function() {

				// grab wrapper element inside content
				var wrap = this.getOverlay().find(".contentWrap");

				// load the page specified in the trigger
				wrap.load(this.getTrigger().attr("href"));
			}

		});
		if(result.entries != null && result.entries.length > 0) {
			$('#downloadBtn').removeClass("hidden");
		} else {
			$('#downloadBtn').addClass("hidden");
		}
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