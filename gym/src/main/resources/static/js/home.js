
 $(document).ready(function () {
     let body = document.querySelector('.bodyContents');
     body.style.marginTop = "0px";


    // 페이지가 로드될 때 스크롤을 맨 위로 이동
    $(window).on('beforeunload', function() {
        window.scrollTo(0, 0);
    });

    function animateOnScroll() {
        //-------------------------------처음부분----------------------------------------
        // 처음 부분의 링크 및 설명 요소의 스타일을 초기화
        $('#mainPageFirstLink').css({
            'opacity': '1',
        });

        $('#mainPageFirstExplain1').css({
            'opacity': '1',
        });

        //-------------------------------맵----------------------------------------
        // 각 맵 관련 요소의 위치를 체크하여 애니메이션을 적용
        $('#mainPageMapExplain1, #mainPageMapExplain2, #mainPageMapExplain3, #mainPageMapLink').each(function () {
            var elementTop = $('#mainPageMapContainer').offset().top; // 현재 요소의 상단 위치
            var viewportBottom = $(window).scrollTop() + $(window).height(); // 뷰포트 하단 위치

            // 요소가 뷰포트에 들어오면 스타일을 적용
            if (elementTop < viewportBottom - 500) {
                $(this).css({
                    'transform': 'translateY(0)',
                    'opacity': '1',
                });
            }
        });

        // 추가적인 맵 링크 애니메이션
        $('#mainPageMapLink, #mainPageMap1').each(function () {
            var elementTop = $('#mainPageMapContainer').offset().top;
            var viewportBottom = $(window).scrollTop() + $(window).height();

            if (elementTop < viewportBottom - 500) {
                $(this).css({
                    'transform': 'translateY(0)',
                    'opacity': '1',
                });
            }
        });

        //-------------------------------유니폼----------------------------------------
        // 유니폼 관련 요소 애니메이션
        $('#mainPageUniformExplain, #mainPageUniformLink').each(function () {
            var elementTop = $('#mainPageUniformContainer').offset().top;
            var viewportBottom = $(window).scrollTop() + $(window).height();

            if (elementTop < viewportBottom - 500) {
                $(this).css({
                    'transform': 'translateY(0)',
                    'opacity': '1',
                });
            }
        });

        // 유니폼 이미지 애니메이션
        $('#mainPageUniform1, #mainPageUniform2, #mainPageUniform3').each(function () {
            var elementTop = $('#mainPageUniformContainer').offset().top;
            var viewportBottom = $(window).scrollTop() + $(window).height();

            if (elementTop < viewportBottom - 500) {
                $(this).css({
                    'transform': 'translateX(0)',
                    'opacity': '1',
                });
            }
        });

        //---------------------------------갤러리----------------------------------------
        // 갤러리 관련 요소 애니메이션
        $('#mainPageGalleryExplain1, #mainPageGalleryExplain2, #mainPageGalleryLink').each(function () {
            var elementTop = $('#mainPageGalleryContainer').offset().top;
            var viewportBottom = $(window).scrollTop() + $(window).height();

            if (elementTop < viewportBottom - 500) {
                $(this).css({
                    'transform': 'translateY(0)',
                    'opacity': '1',
                });
            }
        });

        // 갤러리 이미지 애니메이션
        $('#mainPageGallery1, #mainPageGallery2').each(function () {
            var elementTop = $('#mainPageGalleryContainer').offset().top;
            var viewportBottom = $(window).scrollTop() + $(window).height();

            if (elementTop < viewportBottom - 500) {
                $(this).css({
                    'transform': 'translateY(0)',
                    'opacity': '1',
                });
            }
        });

        // 링크에 마우스 오버 이벤트 추가
        $("#mainPageUniformLink, #mainPageGalleryLink, #mainPageMapLink").hover(
            function() {
                $(this).find('a').css({"background-color":"#333333", "color" : "whitesmoke"});
            },
            function() {
                $(this).find('a').css({"background-color":"black", "color" : "white"});
            }
        );
    }

    // 페이지 로드 시 및 스크롤할 때 애니메이션 체크
    $(window).on('scroll', animateOnScroll);
    animateOnScroll(); // 초기 실행
});
 