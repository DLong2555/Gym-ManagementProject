document.addEventListener('DOMContentLoaded', () => {

    const regionMap = new Map();
    regionMap.set("강원", ["강릉시", "동해시", "삼척시", "속초시", "원주시", "춘천시", "태백시", "고성군", "양구군", "양양군", "영월군", "인제군", "정선군", "철원군", "평창군", "홍천군", "화천군", "횡성군"]);
    regionMap.set("경기", ["고양시", "과천시", "광명시", "광주시", "구리시", "군포시", "김포시", "남양주시", "동두천시", "부천시", "성남시", "수원시", "시흥시", "안산시", "안성시", "안양시", "양주시", "오산시", "용인시", "의왕시", "의정부시", "이천시", "파주시", "평택시", "포천시", "하남시", "화성시", "가평군", "양평군", "여주군", "연천군"]);
    regionMap.set("경남", ["거제시", "김해시", "마산시", "밀양시", "사천시", "양산시", "진주시", "진해시", "창원시", "통영시", "거창군", "고성군", "남해군", "산청군", "의령군", "창녕군", "하동군", "함안군", "함양군", "합천군"]);
    regionMap.set("경북", ["경산시", "경주시", "구미시", "김천시", "문경시", "상주시", "안동시", "영주시", "영천시", "포항시", "고령군", "군위군", "봉화군", "성주군", "영덕군", "영양군", "예천군", "울릉군", "울진군", "의성군", "청도군", "청송군", "칠곡군"]);
    regionMap.set("광주", ["광산구", "남구", "동구", "북구", "서구"]);
    regionMap.set("대구", ["남구", "달서구", "동구", "북구", "서구", "수성구", "중구", "달성군"]);
    regionMap.set("대전", ["대덕구", "동구", "서구", "유성구", "중구"]);
    regionMap.set("부산", ["강서구", "금정구", "남구", "동구", "동래구", "부산진구", "북구", "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구", "기장군"]);
    regionMap.set("서울", ["강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"]);
    regionMap.set("울산", ["남구", "동구", "북구", "중구", "울주군"]);
    regionMap.set("인천", ["계양구", "남구", "남동구", "동구", "부평구", "서구", "연수구", "중구", "강화군", "옹진군"]);
    regionMap.set("전남", ["광양시", "나주시", "목포시", "순천시", "여수시", "강진군", "고흥군", "곡성군", "구례군", "담양군", "무안군", "보성군", "신안군", "영광군", "영암군", "완도군", "장성군", "장흥군", "진도군", "함평군", "해남군", "화순군"]);
    regionMap.set("전북", ["군산시", "김제시", "남원시", "익산시", "전주시", "정읍시", "고창군", "무주군", "부안군", "순창군", "완주군", "임실군", "장수군", "진안군"]);
    regionMap.set("제주", ["서귀포시", "제주시", "남제주군", "북제주군"]);
    regionMap.set("충북", ["제천시", "청주시", "충주시", "괴산군", "단양군", "보은군", "영동군", "옥천군", "음성군", "증평군", "진천군", "청원군"]);
    regionMap.set("충남", ["계룡시", "공주시", "금산군", "논산시", "당진시", "보령시", "부여군", "서산시", "세종시", "아산시", "예산군", "천안시", "청양군", "태안군", "홍성군"]);

    const cityMap = new Map();
    cityMap.set("천안시", ["목천읍", "풍세면", "광덕면", "성환읍", "성거읍", "직산읍", "입장면", "성정동", "쌍용동", "백석동", "불당동", "부성동", "북면", "성남면", "수신면", "병천면", "동면", "중앙동", "문성동", "원성동", "봉명동", "일봉동", "신방동", "청룡동", "신안동"]);
    cityMap.set("아산시", ["염치읍", "배방읍", "송악면", "탕정면", "음봉면", "둔포면", "영인면", "인주면", "선장면", "도고면", "신창면", "온양"]);
    cityMap.set("세종시", ["전의면", "조치원읍", "연기면", "소정면", "장군면", "연서면", "한솔동", "도담동", "고운동", "세종동", "아름동", "어진동"]);

    const region = document.getElementById("region");
    const city = document.getElementById("city");
    const state = document.getElementById("state");
    region.addEventListener("change", () => {
        const value = region.value;
        const selectedCities = regionMap.get(value);

        city.options.length = 0;

        const defaultOption = document.createElement("option");
        defaultOption.value = "";
        defaultOption.textContent = "전체";
        city.appendChild(defaultOption);

        selectedCities.forEach(function (item) {
            const option = document.createElement("option");
            option.value = item;
            option.textContent = item;
            city.appendChild(option);

        })

    })

    city.addEventListener("change", (e) => {
        const value = city.value;
        const selectedState = cityMap.get(value);

        state.options.length = 0;

        const defaultOption = document.createElement("option");
        defaultOption.value = "";
        defaultOption.textContent = "전체";
        state.appendChild(defaultOption);

        selectedState.forEach(function (item) {
            const option = document.createElement("option");
            option.value = item;
            option.textContent = item;
            state.appendChild(option);
        })

    })

    const mapBox = document.getElementById("mapBox");
    const btn = document.getElementById("gymListBtn");
    const gymType = document.getElementById("gymType");
    const gymListBox = document.getElementById("gymNameListBox");
    const markerMap = new Map();
    const registerFormBtn = document.querySelector(".registerChildBtn");

    btn.addEventListener("click", function () {
        let searchBox = document.querySelector(".searchBox");
        registerFormBtn.style.display = "none";

        if(region.value === ""){
            alert("지역 선택은 필수입니다.")
            return false;
        }

        let reqValue = createSearchQuery(region.value, city.value, state.value, gymType.value, searchBox.value);

        let reqJson = {}
        reqJson.searchQuery = reqValue;

        //ajax
        let request = new XMLHttpRequest();
        request.onreadystatechange = function () {
            if (request.readyState === 4) {
                if (request.status === 200) {

                    let result = request.response;

                    if (result.length === 0) {
                        console.log("No search results available.");
                    } else {
                        mapBox.style.display = "flex";

                        // 첫 번째 항목 가져오기
                        let firstResult = result[0];
                        let mapx = firstResult.mapx || 127.105399; // 기본 경도
                        let mapy = firstResult.mapy || 37.3595704; // 기본 위도

                        // 지도 초기화
                        const mapOptions = {
                            center: new naver.maps.LatLng(mapy, mapx),
                            zoom: 14,
                            mapTypeControl: false,
                        };
                        const map = new naver.maps.Map('map', mapOptions);

                        const names = document.getElementById("gymNameListBox");
                        names.innerHTML = "";

                        // 모든 검색 결과에 대해 마커 추가
                        result.forEach(value => {
                            const id = value.gymId;
                            const title = decoding(value.title);
                            const address = value.address;
                            const lat = value.mapy; // 위도
                            const lng = value.mapx; // 경도

                            const div = document.createElement("div");
                            const addressDiv = document.createElement("div");

                            const gymListDiv = `
                                <div class="gymNames">
                                    <div class="gymDiv">${title}</div>
                                    <div class="gymAddressDiv" style="display: none">${address}</div>
                                    <input type="hidden" class="gymId" value="${id}"></input>
                                </div>                              
                            `;

                            names.innerHTML += (gymListDiv);

                            addMarker(lat, lng, id, title, address);
                        });

                        //클릭 시 마커 크지 조절
                        function createGymMarkerContent(gymName, isHighlighted) {
                            const width = isHighlighted ? 50 : 25;
                            const height = isHighlighted ? 68 : 34;

                            return `
                                <div style="position: relative; text-align: center;">
                                    <img class="gymMarker" src="/image/gymMarker.png" style="width: ${width}px; height: ${height}px;">
                                    <div style="
                                        position: absolute;
                                        bottom: -18px;
                                        left: 50%;
                                        transform: translateX(-50%);
                                        padding: 2px 6px;
                                        font-size: 12px;
                                        font-weight: bold;
                                        color: #333;
                                        border: none;
                                        white-space: nowrap;
                                    ">
                                        ${gymName}
                                    </div>
                                </div>
                            `;
                        }

                        // 마커 추가 함수 및 마커 클릭시 하이라이트 및 취소
                        function addMarker(lat, lng, id, title, address) {
                            const marker = new naver.maps.Marker({
                                position: new naver.maps.LatLng(lat, lng, title),
                                map: map,
                                title: decoding(title),
                                address: address,
                                icon: {
                                    content: createGymMarkerContent(title, false)
                                }
                            });

                            markerMap.set(title, marker);

                            naver.maps.Event.addListener(marker, "click", function () {

                                markerMap.forEach((m, key) => {
                                    m.setIcon({
                                        content: createGymMarkerContent(key, false)
                                    })
                                })

                                marker.setIcon({
                                    content: createGymMarkerContent(title, true)
                                });

                                map.setCenter(marker.getPosition());

                                highlight(id, title, address);

                            });
                        }

                        // 리스트 클릭시 하이라이트 또는 취소
                        gymListBox.addEventListener("click", function (event) {
                            if (event.target.classList.contains("gymDiv")) {
                                const gymName = event.target.textContent;
                                const gymAddress = event.target.closest(".gymNames").querySelector(".gymAddressDiv").textContent;
                                const gymId = event.target.closest(".gymNames").querySelector(".gymId").value;

                                const marker = markerMap.get(gymName);

                                cancelHighlightAll();

                                if (marker) {

                                    markerMap.forEach((m,key) =>{
                                        m.setIcon({content: createGymMarkerContent(key, false)})
                                    })

                                    marker.setIcon({
                                        content: createGymMarkerContent(gymName, true)
                                    });

                                    map.setCenter(marker.getPosition());

                                    highlight(gymId, gymName, gymAddress);

                                }
                            }
                        });

                        //특수문자 디코딩
                        function decoding(text){
                            let textArea = document.createElement("textarea");
                            textArea.innerHTML = text;
                            return textArea.value;
                        }

                        //선택대상 하이라이트 함수
                        function highlight(id, title, gymAddress) {
                            let gymDivs = document.querySelectorAll(".gymDiv");
                            let gymAddressDivs = document.querySelectorAll(".gymAddressDiv");

                            setSelectedBox(id, title);

                            gymDivs.forEach(div => {
                                if (div.textContent === title) {
                                    div.style.backgroundColor = "lightgray";
                                    div.style.fontWeight = "bold";
                                    div.style.borderColor = "lightgray";
                                } else {
                                    div.style.backgroundColor = "white";
                                    div.style.fontWeight = "normal";
                                    div.style.borderColor = "white";
                                }
                            });

                            gymAddressDivs.forEach(address => {
                                if(address.textContent === gymAddress) {
                                    address.style.display = "flex";
                                    address.style.backgroundColor = "lightgray";
                                    address.style.fontWeight = "bold";
                                }else {
                                    address.style.display = "none";
                                }
                            })

                        }

                        //전체 하이라이트 취소
                        function cancelHighlightAll() {
                            let gymDivs = document.querySelectorAll(".gymDiv");

                            gymDivs.forEach(div => {
                                div.style.backgroundColor = "white";
                                div.style.fontWeight = "normal";
                            });
                        }

                        function setSelectedBox(id, title) {
                            let selectedId = document.getElementById("gymId");
                            let selectedGymName = document.getElementById("gymName");

                            registerFormBtn.style.display = "block";

                            selectedId.value = id;
                            selectedGymName.value = title;
                        }

                    }
                }
            }
        }

        request.open("POST", "/gym/search", true);
        request.responseType = "json";
        request.setRequestHeader("Content-Type", "application/json");
        request.send(JSON.stringify(reqJson));

    })

    gymListBox.addEventListener("mouseover", function (event) {
        if (event.target.classList.contains("gymDiv")) {
            if (event.target.style.fontWeight !== "bold") {
                event.target.style.backgroundColor = "rgb(248, 249, 249)";
                event.target.style.color = "gray";
            }
        }
    });

    gymListBox.addEventListener("mouseout", function (event) {
        if (event.target.classList.contains("gymDiv")) {
            if (event.target.style.fontWeight !== "bold") {
                event.target.style.backgroundColor = "white";
            }

            event.target.style.color = "rgb(66, 73, 73)";
        }
    });

    function createSearchQuery(region, city, state, gymType, query){
        let q = "";

        if(city === ""){
            if(query === "") q = region + " " + gymType;
            else q = region + " " + gymType + " " + query;
        }else{
            if(state === ""){
                if(query !== "") q = city + " " + gymType + " " + query;
                else q = city + " " + gymType.value;
            }else{
                if(query === "")  q = city + " " + state + " " + gymType;
                else q = city + " " + state + " " + gymType + " " + query;
            }
        }

        return q;
    }
})