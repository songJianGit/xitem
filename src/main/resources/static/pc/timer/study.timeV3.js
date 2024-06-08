let TimerTracker = function (trace_url, resource_id, resource_type, time_interval, time_max) {
    let period_id;
    let time_max_accumulate = 0;
    let watch_dog;

    function trace() {
        let ajaxTimeout = $.ajax({
            "type": "POST",
            "url": trace_url,
            "cache": false,
            "dataType": "json",
            "timeout": 6000, // 超时时间设置，单位毫秒
            "data": {
                "periodId": period_id,
                "resourceType": resource_type,
                "resourceId": resource_id
            },
            "success": function (data, textStatus, jqXHR) {
                period_id = data.data;
            },
            "error": function (event, jqXHR, ajaxSettings, thrownError) {
                console.error(event);
            },
            "complete": function (XMLHttpRequest, status) { //请求完成后最终执行参数
                if (status == 'timeout') {//超时,status还有success,error等值的情况
                    ajaxTimeout.abort();//取消本次请求
                    console.log("网络不稳定");
                }
            }
        });
    }

    function monitor2() {
        trace();
        time_max_accumulate += parseInt(time_interval);
        if (time_max_accumulate >= time_max) {// 到达最大计时
            console.log("exceed time_max:" + time_max);
            clearInterval(watch_dog);
        }
    }

    // 禁止用F5键(有点用，不保证任何时候都有效)
    function disF5() {
        $(document).bind('keydown', function (e) {
            let key = e.which;
            if (key == 116) {
                e.preventDefault();
            }
        });
    }

    return {
        "start": function () {
            disF5();
            trace();
            watch_dog = setInterval(monitor2, time_interval * 1000);// time_interval的单位是秒
        },
        "end": function () {
            trace();
            clearInterval(watch_dog);
        }
    }
};