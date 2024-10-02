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


// 역이름을 넣고 역이름 옆의 버튼을 눌렀을 때 (밑에 있는 호선에 대한 데이터가 바뀜)
$('.search-btn1').on('click', function() {
    var query = $('#searchInput').val();

    // console.log('역이름: ' + query); // 로그를 통해 값 확인
    if (!query || query.trim() === '') {
        alert('역 이름을 입력해주세요!');
        $('#searchInput').empty();
        return;
    }

    $.ajax({
        type: 'GET',
        url: '/goSearch/stationLines', // 역의 호선 정보를 가져오는 API
        data: { stationName: query }, // 역 이름을 전송
        dataType: 'json',
        success: function(data) {
            // 기존의 select 박스 비우기
            $('#stationLineList').empty();

            // 중복 제거를 위한 Set 사용
            var seenStationLine = new Set();

            // 고유한 stationLine들만 필터링
            var uniqueStationLine = data.filter(function(station) {
                if (seenStationLine.has(station.stationLine)) {
                    return false; // 중복되면 제외
                }
                seenStationLine.add(station.stationLine);
                return true; // 고유한 경우만 포함
            });

            // 필터링된 호선 정보를 select 박스에 추가
            uniqueStationLine.forEach(function(station) {
                $('#stationLineList').append('<option value="' + station.stationLine + '">' + station.stationLine + '호선' + '</option>');
            });
        },
        error: function(err) {
            console.log(err);

        }
    });
});


document.querySelector('.search-btn2').addEventListener('click', function (event) {
    event.preventDefault();

    let stationName = document.getElementById('searchInput').value;
    let stationLine = document.getElementById('stationLineList').value;
    let ampm = document.getElementById('ampm').value;
    let hour = document.getElementById('hour').value;
    let minute = document.getElementById('minute').value;

    if (!stationName || !ampm || !hour || !minute || !stationLine) {
        alert('모든 값을 입력해주세요!');
        return;
    }

    $.ajax( {
        type: 'POST',
        url: `/goSearch/result`,
        // dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            stationName: stationName,
            stationLine: stationLine,
            ampm: ampm,
            hour:hour,
            minute: minute
        }),
        success: function (response) {
            console.log('응답내용: ',response);

            document.getElementById('result').innerHtml = '';
            $('#result').empty();

            response.forEach(function(result) {

                document.getElementById('result').insertAdjacentHTML('beforeend',
                    `<div>${result.stationName}, ${result.stationLine}, ${result.directionType}, ${result.congestion}</div>`)
            })

            $('#stationLineList').empty();
            document.getElementById('searchInput').value = '';
            $('#ampm').val('AM');
            $('#hour').val('firstHour');
            $('#minute').val('firstMinute');
            
        },
        error: function(err) {
            console.log('에러: ', err);
        }
    });
});
