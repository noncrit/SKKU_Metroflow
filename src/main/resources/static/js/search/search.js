$('#searchInput').on('input', function() {
    var query = $('#searchInput').val();

    console.log(query);

    if (query.trim() === '') {
        $('#stationList').empty().hide();
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

$(document).click(function(event) {
    // 리스트 이외의 다른 곳을 클릭했을때 리스트 사라짐, 나중에 다시
    if (!$(event.target).closest('#searchInput, #stationList').length) {
        console.log('리스트 숨기기')
        $('#stationList').hide().empty();
      }
});


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
            $('#stationLineList').append('<option value="">' + '호선' + '</option>');

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

    // 결과창에 시간 표시
    document.getElementById('input-time').textContent = `${ampm} ${hour}시 ${minute}분`

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
        beforeSend: function(xhr) {
            // CSRF 토큰과 헤더를 설정
            const csrfToken = document.querySelector('meta[name="_csrf"]').content;
            const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function (response) {
            // console.log('응답내용: ',response);

            displayResults(response);

            // 검색후에 값 초기화
            $('#stationLineList').empty();
            $('#stationLineList').append('<option value="">' + '호선' + '</option>');
            document.getElementById('searchInput').value = '';
            $('#ampm').val('AM');
            $('#hour').val('');
            $('#minute').val('');

        },
        error: function(err) {
            console.log('에러: ', err);

            const messageElement = document.getElementById('result-container');
            messageElement.textContent = '혼잡도 데이터가 없습니다'
            messageElement.style.display = 'block';

            $('#stationLineList').empty();
            $('#stationLineList').append('<option value="">' + '호선' + '</option>');
            document.getElementById('searchInput').value = '';
            $('#ampm').val('AM');
            $('#hour').val('');
            $('#minute').val('');
        }
    });
});


// 데이터를 받아서 HTML로 변환하는 함수
function displayResults(data) {
    resultContainer.innerHTML = '';  // 기존 결과 초기화

    data.forEach(item => {
        const statusColor = getStatusColor(item.congestion);
        const lineColor = lineColors[item.stationLine] || "#000000";

        const resultItem = `
            <div class="result-item">
                <div class="left-section">
                    <span class="line-number" style="background-color: ${lineColor};">${item.stationLine}</span>
                    <span class="station-name">${item.stationName}</span>
                </div>
                <div class="status-bar">
                    <span class="status-time">${item.directionType}</span>
                    <div class="progress-bar">
                        <div class="progress" style="width: 100%; background-color: ${statusColor};"></div>
                    </div>
                    <span class="status-label" style="color: ${statusColor};">${getCongestion(item.congestion)}</span>
                </div>
            </div>
        `;

        resultContainer.innerHTML += resultItem;
    });
}

const lineColors = {
    1: "#0032A0", // 1호선
    2: "#00B140", // 2호선
    3: "#FC4C02", // 3호선
    4: "#00A9E0", // 4호선
    5: "#A05EB5", // 5호선
    6: "#A9431E", // 6호선
    7: "#67823A", // 7호선
    8: "#E31C79", // 8호선
    9: "#8C8279", // 9호선
};

const resultContainer = document.getElementById('result-container');

function getStatusColor(congestion) {
    if (congestion <= 33) {
        return "green";    // 여유
    } else if (congestion > 33 && congestion <= 66) {
        return "orange"; // 보통
    } else if (congestion > 66) {
        return "red";  // 혼잡
    } else {
        return "gray";   // 기본값
    }
}

function getCongestion(congestion) {
    if (congestion <= 33) {
        return "여유";    // 여유
    } else if (congestion > 33 && congestion <= 66) {
        return "보통"; // 보통
    } else if (congestion > 66) {
        return "혼잡";  // 혼잡
    } else {
        return "오류";   // 기본값
    }
}