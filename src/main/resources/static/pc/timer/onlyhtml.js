/**
 * 业务页面打开控制
 * @param type 业务类型（相同业务，打开多个，便会提示）
 * @param key1 一般传业务id就行
 * @param msg 提示信息
 * @returns {{init: init}}
 * @constructor
 */
let OneHtm = function (type, key1, msg) {
    let k = "kt" + type;
    key1 = "kid" + key1;

    function computeTheTimeDifference() {
        let infos = jQuery.cookie(k);
        if (infos != undefined && infos != null && infos != '') {
            let info = infos.split(",")
            let timestamp1 = info[0];
            let key2 = info[1];
            let timestamp2 = new Date().getTime();
            let timestamp3 = timestamp2 - timestamp1;
            if (timestamp3 < 1200 && key2 != key1) {
                // 时间差小于1200毫秒，表示有页面在显示。该页面不能再打开
                alert(msg);
                window.history.back();
                return false;
            }
        }
        return true;
    }

    return {
        init: function () {
            let t = setInterval(function () {
                if (computeTheTimeDifference()) {
                    jQuery.cookie(k, new Date().getTime() + "," + key1);
                } else {
                    clearInterval(t);
                }
            }, 1000);
        }
    }
};