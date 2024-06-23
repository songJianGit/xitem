<!DOCTYPE html>
<html lang="zh">
<head>
    <title>${examTitle!}</title>
    <#include "../commons/head.ftl"/>
    <style>
        .q-item-title {
            margin-top: 15px;
            font-size: 16px;
        }

        .q-item-op-box {
            display: flex;
            flex-direction: column;
            margin-top: 7px;
            margin-bottom: 7px;
            margin-left: 7px;
        }

        .q-item-op {
            display: flex;
            margin-top: 7px;
            margin-bottom: 7px;
            background-color: #F5F7FA;
            padding: 13px;
            border-radius: 4px;
        }

        .q-item-op-put-label-style {
            width: 100%;
            margin-left: 1%;
            margin-bottom: 0;
        }

        .top-info {
            z-index: 1;
            display: flex;
            justify-content: space-between;
            position: fixed;
            top: 0;
            width: 100%;
            padding: 7px;
            background-color: #fff;
            border-bottom: 2px solid #e7e7e7;
        }

        .bottom-btns {
            position: fixed;
            bottom: 0;
            width: 100%;
            margin: 0;
            display: flex;
            justify-content: space-around;
            padding: 7px;
            background-color: #fff;
            border-top: 2px solid #e7e7e7;
        }

        .q-item-box {
            margin-top: 27px;
            margin-bottom: 50px;
            border: 0;
        }

        /*大屏下的显示调整*/
        .pc-auto {
            max-width: 900px;
        }

        .countdownInfo-box {
            line-height: 36px;
        }
    </style>
</head>
<body>
<div class="pc-auto" style="margin: 0 auto">
    <div class="top-info pc-auto">
        <div class="countdownInfo-box">剩余时间：<span class="countdownInfo">00:00:00</span></div>
        <div>
            <button type="button" class="btn btn-primary" onclick="saveAnswer(2)">交卷</button>
        </div>
    </div>
    <div class="card q-item-box pc-auto">
        <div class="card-body">
            <div class="q-item-title"></div>
            <div class="q-item-op-box">
            </div>
        </div>
    </div>
    <div class="bottom-btns pc-auto">
        <button type="button" class="btn btn-primary bottom-btn" id="examSheet">答题卡</button>
        <button type="button" class="btn btn-primary bottom-btn" id="prevBtn">上一题</button>
        <button type="button" class="btn btn-primary bottom-btn" id="nextBtn">下一题</button>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    let pageNum = 1;// 当前页
    let userPaperQuestions;// 当前试卷题目集合
    let action_user_paper_question_id = '';// 当前题id

    $(function () {
        getUserPaperQuestions();// 获取题目ids
        timeInfo();// 计时
    });

    function getUserPaperQuestions() {
        let indexLoad = layer.load(1, {// 遮罩层
            shade: [0.5, '#fff']
        });
        $.ajax({
            url: "${ctx.contextPath}/pc/exam/userPaperQuestionIds?userPaperId=${userPaperId!}",
            cache: false,// 不缓存
            success: function (data) {
                layer.close(indexLoad);
                if (data.result) {
                    userPaperQuestions = data.data;
                    getQuestion();// 第一个问题显示
                } else {
                    layer.msg(data.msg);
                }
            }
        });
    }

    $("#examSheet").click(function () {
        saveAnswer(4);
    });

    $("#prevBtn").click(function () {
        if (pageNum > 1) {
            pageNum--;
            saveAnswer(0);
        } else {
            layer.msg("已是第一题");
        }
    });

    $("#nextBtn").click(function () {
        if (pageNum < userPaperQuestions.length) {
            pageNum++;
            saveAnswer(1);
        } else {
            layer.msg("已是最后一题");
        }
    });

    /**
     * 答题卡题目跳转回调
     * @param qNum
     */
    function examSheetCallback(qNum) {
        pageNum = qNum;
        getQuestion();
    }

    /**
     * 获取题目信息
     */
    function getQuestion() {
        if (isBlank(action_user_paper_question_id)) {
            action_user_paper_question_id = userPaperQuestions[0];
        }
        let indexLoad = layer.load(1, {// 遮罩层
            shade: [0.5, '#fff']
        });
        $.ajax({
            url: "${ctx.contextPath}/pc/exam/getQuestion",
            cache: false,// 不缓存
            data: {
                nextQid: userPaperQuestions[pageNum - 1]
            },
            success: function (data) {
                layer.close(indexLoad);
                if (data.result) {
                    let question = data.data;
                    action_user_paper_question_id = question.userPaperQuestionId;// 当前题目id
                    $(".q-item-title").html(pageNum + ".&nbsp;" + question.title);// 题目标题
                    $(".q-item-op-box").html(getQuestionOptionHtm(question));// 选项
                    setAnswer(question.userAnswerIds);
                } else {
                    layer.msg(data.msg);
                }
            }
        });
    }

    /**
     * 保存题目答案，并在成功响应后，调用各类事件。
     * @param functiontype 0-上一页 1-下一页 2-检查未答题目后交卷 3-交卷
     */
    function saveAnswer(functiontype) {
        let indexLoad = layer.load(1, {// 遮罩层
            shade: [0.5, '#fff']
        });
        let answers = getAnswer();
        $.ajax({
            url: "${ctx.contextPath}/pc/exam/saveAnswer",
            cache: false,// 不缓存
            type: "post",
            data: {
                qid: action_user_paper_question_id,
                answers: answers
            },
            success: function (data) {
                layer.close(indexLoad);
                if (data.result) {
                    if (functiontype == 0 || functiontype == 1) {
                        getQuestion();
                    }
                    if (functiontype == 2) {
                        checkBlankNumAndSub();
                    }
                    if (functiontype == 3) {
                        onSubFUN();
                    }
                    if (functiontype == 4) {
                        layer_show("答题卡", "${ctx.contextPath}/pc/exam/examSheet?userPaperId=${userPaperId!}");
                    }
                } else {
                    layer.msg(data.msg);
                }
            }
        });
    }

    /**
     * 答案的回显
     * @param answer
     */
    function setAnswer(answer) {
        if (isNotBlank(answer)) {
            let answerS = answer.split(",");
            for (let i = 0; i < answerS.length; i++) {
                $(".q-item-op-" + answerS[i]).prop("checked", true);
            }
        }
    }

    /**
     * 答案的获取
     * @returns {*|string}
     */
    function getAnswer() {
        let vals = $("input[name='val-input']:checked").map(function () {
            return this.value;
        }).get();
        if (isBlank(vals)) {
            return '';
        } else {
            return vals.join(",");
        }
    }

    /**
     * 获取题目的选项html
     * @param question
     * @returns {string}
     */
    function getQuestionOptionHtm(question) {
        let questionOptionList = question.questionOptionList;
        let type = question.qtype;// 0-是非 1-单选 2-多选
        let inputType = '';
        if (type == 0 || type == 1) {
            inputType = 'radio';
        }
        if (type == 2) {
            inputType = 'checkbox';
        }
        let htm = '';
        for (let i = 0; i < questionOptionList.length; i++) {
            htm += optionHtm(questionOptionList[i].title, questionOptionList[i].id, inputType);
        }
        return htm;
    }

    /**
     * 题目选项的html元素拼接
     * @param lable
     * @param val
     * @param type
     * @returns {string}
     */
    function optionHtm(lable, val, type) {
        let htm = '';
        if (isNotBlank(lable)) {
            htm += '<div class="q-item-op">';
            htm += '<input id="op-id-' + val + '" class="q-item-op-' + val + '" name="val-input" value="' + val + '" type="' + type + '"/>';
            htm += '<label class="q-item-op-put-label-style" for="op-id-' + val + '">' + lable + '</label>';
            htm += '</div>';
        }
        return htm;
    }

    /**
     * 检查是否有题目还未作答，然后提交试卷
     */
    function checkBlankNumAndSub() {
        let indexLoadSub = layer.load(1, {// 遮罩层
            shade: [0.5, '#fff']
        });
        $.ajax({
            url: "${ctx.contextPath}/pc/exam/checkBlankNum",
            cache: false,// 不缓存
            data: {
                "userPaperId": '${userPaperId!}'
            },
            success: function (data) {
                layer.close(indexLoadSub);
                let infos = data.data;
                let msg = "";
                if (infos.length != 0) {
                    msg = '第' + infos.join("，") + "题未作答，"
                }
                layer.confirm(msg + '确定提交？', {
                    title: '提示',
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    onSubFUN();
                }, function () {
                    // 取消
                });
            }
        });
    }

    /**
     * 交卷
     */
    function onSubFUN() {
        let indexLoadSub = layer.load(1, {// 遮罩层
            shade: [0.5, '#fff']
        });
        $.ajax({
            url: "${ctx.contextPath}/pc/exam/examPageSubmit",
            cache: false,// 不缓存
            data: {
                "userPaperId": '${userPaperId!}'
            },
            success: function (data) {
                layer.close(indexLoadSub);
                if (data.result) {
                    window.location.href = "${ctx.contextPath}/pc/exam/examSubmitOk?userPaperId=${userPaperId!}";
                } else {
                    alert(data.msg);
                }
            }
        });
    }

    /**
     * 1.倒计时显示
     * 2.结束时间过后的自动提交
     */
    function timeInfo() {
        let time = setInterval(function () {
            countdownInfo('${endTime!}');
            if (compareDateStringWithNow('${endTime!}')) {
                clearInterval(time);
                layer.msg("考试时间已到，自动交卷");
                let indexLoad = layer.load(1, {// 遮罩层
                    shade: [0.5, '#fff']
                });
                setTimeout(function () {
                    saveAnswer(3);
                    layer.close(indexLoad);
                }, 1500);
            }
        }, 1000);
    }

    function compareDateStringWithNow(dateString) {
        dateString = new Date(dateString.replace(/-/g, '/'));
        let inputDate = new Date(dateString);
        let currentDate = new Date();
        if (inputDate > currentDate) {
            // return '输入的时间大于当前时间';
            return false;
        } else if (inputDate < currentDate) {
            // return '输入的时间小于当前时间';
            return true;
        } else {
            // return '输入的时间等于当前时间';
            return true;
        }
    }

    /**
     * 结束时间和当前时间的时间差显示
     * @param endStr 考试结束时间
     */
    function countdownInfo(endStr) {
        endStr = new Date(endStr.replace(/-/g, '/'));
        let endDate = new Date(endStr);
        // 计算时间差，单位为毫秒
        let diffMs = Math.abs(endDate.getTime() - new Date().getTime());
        // 将毫秒转换为小时、分钟、秒
        let hours = Math.floor(diffMs / 3600000);
        let minutes = Math.floor((diffMs % 3600000) / 60000);
        let seconds = Math.floor((diffMs % 60000) / 1000);
        // 格式化输出为HH:mm:ss
        let formattedDiff = hours.toString().padStart(2, '0') + ":" + minutes.toString().padStart(2, '0') + ":" + seconds.toString().padStart(2, '0');
        // console.log(formattedDiff); // 输出相差的时间，格式为HH:mm:ss
        $('.countdownInfo').text(formattedDiff);
    }

</script>
</body>
</html>
