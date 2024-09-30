$('#searchInput').on('input', function() {
    var query = $('#searchInput').val();

    console.log(query);

    if (query.trim() === '') {
        $('#stationList').empty();
        return;
    }

    $.ajax({
        type: 'GET',
        url: '/goSearch/stations',
        data: { search: query},
        dataType: 'json',
        success: function (data) {
            console.log(data);
            $('#stationList').empty();

            var seenStations = new Set();
            var maxItems = 5;
            var uniqueStations = data.filter(function(station) {
                if (seenStations.has(station.stationName)) {
                    return false;
                }
                seenStations.add(station.stationName);
                return true;
            });

            uniqueStations.slice(0, maxItems).forEach(function(station) {
                $('#stationList').append('<li>' + station.stationName + '</li>').show();
            });
        },
        error: function(err) {
            console.log('오류: ', err);
            // alert('오류가 발생했습니다.');
        }
    });
});

$('#stationList').on('click', 'li', function() {
    var stationName = $(this).text();
    $('#searchInput').val(stationName);
    $('#stationList').hide().empty();
});

// $(document).click(function(event) {
//     리스트 이외의 다른 곳을 클릭했을때 리스트 사라짐, 나중에 다시
//     if (!$(event.target).closest('#searchInput, #stationList'.length)) {
//         $('stationList').hide().empty();
//       }
// });

