﻿﻿layui.use(['element', 'jquery', 'form', 'layedit', 'flow'], function () {
    let $ = layui.jquery;
    let layedit = layui.layedit;

    //留言的编辑器
    let editIndex = layedit.build('remarkEditor', {
        height: 200,
        tool: ['face', 'strong' //加粗
            ,'italic' //斜体
            ,'underline' //下划线
            ,'del' //删除线
            ,'link'
        ],
    });


    //留言的编辑器的验证
    $("#submit-comment").click(function(){
        let content =  layedit.getContent(editIndex);
        let parentId =  $("#parent-id").val();
        if(parentId == ""){
            if (content == ""){
                layer.msg('你啥都没输入！', {icon: 5,time:1000,offset:'280px'});
                return;
            }
        }else{
            let replyText = $("#LAY_layedit_1").contents().find(".reply-text").html();
            if (!replyText || replyText == "&nbsp;"){
                layer.msg('你啥都没输入！', {icon: 5,time:1000,offset:'280px'});
                return;
            }
        }

        layer.open({
            type: 1,
            content: $('#comment-box'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            area: ['300px', '250px'],
            title: ['评论信息', 'font-size:18px;'],
            offset:'200px',
            skin: 'layui-layer-molv'
        });
    });


    //提交留言
    $("#sub-comment").click(function(){

        let email  = $("#email-input").val();
        if (email  != ""){
            check("email",email);
        }

        let qq = $("#qq-input").val();
        if (qq != ""){
            check("qq",qq);
        }
        let nickName = $("#user-input").val();
        if (nickName != ""){
            check("nickName",nickName);
        }

        let parentId =  $("#parent-id").val();
        let replyId =  $("#reply-id").val();
        let replyName = $(".btn-reply[data-replyid = '"+replyId+"']").attr("data-targetname");
        let content;
        let replyText = $("#LAY_layedit_1").contents().find(".reply-text").html();
        if (replyText && replyText != "&nbsp;"){
            content = replyText;
        }else{
            content = layedit.getContent(editIndex)
        }
        let jsonContent = {"commentMail":email,"commentQQ":qq,"commentContent":content,"commentNickname":nickName,"parentCommentId":parentId,"replyId":replyId,"replyName":replyName}
        $.ajax({
            url: ctx + "web/comment/submitComment",
            type: 'post',
            data: JSON.stringify(jsonContent),
            processData: false,
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
            },
            success: function (msg) {
                if (msg.code == 200){
                    let comment =  msg.comment;
                    fillingComment(comment);
                    layer.closeAll();
                    layer.msg('评论成功！', {icon: 1,time:1000,offset:'280px'});
                    $("#LAY_layedit_1").contents().find("body").html("");
                }
            }
        });
    })



    $(".btn-reply").click(function(){
        let targetId = $(this).data('parentid'), targetName = $(this).data('targetname'), replyId =  $(this).data('replyid');
        $("#parent-id").val(targetId);
        $("#reply-id").val(replyId);
        let text = "<div style='color:red;display: inline' contenteditable='false' class='reply-name'>@"+targetName+"</div><div style='display: inline' class='reply-text'>&nbsp;</div>";
        $(window).scrollTop($("#LAY_layedit_1").offset().top - 100);
        $("#LAY_layedit_1").contents().find("body").html(text);
    })



    function check(flag,value){
        if (flag == "qq"){
            let reg= /[1-9][0-9]{4,10}/;
            if(!reg.test(value)){
                layer.msg('QQ格式错啦！', {icon: 5,time:1000,offset:'280px'});
                return false;
            }
        }else if(flag == "email"){
            let reg= /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
            if(!reg.test(value)){
                layer.msg('邮箱格式错啦！', {icon: 5,time:1000,offset:'280px'});
                return false;
            }
        }else if(flag == "nickName"){
            let reg= /\d}{1,20}/;
            if(!reg.test(value)){
                layer.msg('昵称太长啦！', {icon: 5,time:1000,offset:'280px'});
                return false;
            }
        }
    }


    function fillingComment(comment){
        if (comment.parentCommentId == null){
            let commentModel = $(".comment-model").clone(true);
            if (comment.commentPicture != "" && comment.commentPicture != null){
                commentModel.find(".picture").attr("src",comment.commentPicture);
            }else {
                commentModel.find(".picture").remove();
            }
            commentModel.find(".username").text(comment.commentNickname);
            commentModel.find(".address").text(comment.commentAddress);
            let createTime = new Date(comment.createTime);
            let createTimeStr =  createTime.format("yyyy/MM/dd hh:ss")
            commentModel.find(".comment-time").text(createTimeStr);
            commentModel.find(".btn-reply").attr("data-parentid",comment.commentId);
            commentModel.find(".btn-reply").attr("data-replyid",comment.commentId);
            commentModel.find(".btn-reply").attr("data-targetname",comment.commentNickname);
            commentModel.find(".comment-content").html(comment.commentContent);
            commentModel.show();
            $(".message-list").prepend(commentModel);
            $(window).scrollTop($(".message-list").offset().top - 50);
        }else{
            let commentChild = $(".comment-child-model").clone(true);
            if (comment.commentPicture != "" && comment.commentPicture != null){
                commentChild.find(".picture").attr("src",comment.commentPicture);
            }else {
                commentChild.find(".picture").remove();
            }
            commentChild.find(".username").text(comment.commentNickname);
            commentChild.find(".address").text(comment.commentAddress);
            let createTime = new Date(comment.createTime);
            let createTimeStr =  createTime.format("yyyy/MM/dd hh:ss")
            commentChild.find(".comment-time").text(createTimeStr);
            commentChild.find(".btn-reply").attr("data-parentid",comment.parentCommentId);
            commentChild.find(".btn-reply").attr("data-replyid",comment.commentId);
            commentChild.find(".btn-reply").attr("data-targetname",comment.commentNickname);
            commentChild.find(".reply-comment-content").html(comment.commentContent);
            let targetParent = $(".comment-parent").find("[data-replyid = '"+comment.parentCommentId+"']");
            let article = targetParent.parents(".comment-parent").parents(".article");
            commentChild.find(".replyName").text(comment.replyName);
            commentChild.show();
            article.append(commentChild);
            $(window).scrollTop(targetParent.offset().top);
        }
    }



});

