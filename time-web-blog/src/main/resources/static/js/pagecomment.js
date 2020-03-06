var area;
layui.use(['element', 'jquery', 'form', 'layedit', 'flow'], function () {
    var element = layui.element;
    var form = layui.form;
    var $ = layui.jquery;
    var layedit = layui.layedit;
    var flow = layui.flow;
    //留言的编辑器
    var editIndex = layedit.build('remarkEditor', {
        height: 100,
        tool: ['face'],
    });
    //留言的编辑器的验证
    $("#submit-comment").click(function(){
        let content =  layedit.getContent(editIndex);
        if (content == ""){
            layer.msg('你啥都没输入！', {icon: 5,offset:'280px'});
            return;
        }
        layer.open({
            type: 1,
            content: $('#comment-box'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            area: ['300px', '250px'],
            title: ['评论信息', 'font-size:18px;'],
            offset:'200px',
            skin: 'layui-layer-molv'
        });
    })
    //提交留言
    $("#sub-comment").click(function(){
        debugger;
        let email  = $("#email-input").val();
        if (email  != ""){
            let reg= /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
            if(!reg.test(email )){
                layer.msg('邮箱格式错啦！', {icon: 5,offset:'280px'});
                return false;
            }
        }

        let qq = $("#qq-input").val();
        if (qq != ""){
            let reg= /[1-9][0-9]{4,10}/;
            if(!reg.test(qq)){
                layer.msg('QQ格式错啦！', {icon: 5,offset:'280px'});
                return false;
            }
        }

        let nickName = $("#user-input").val();
        let content =  layedit.getContent(editIndex);

        let jsonContent = {"commentMail":email,"commentQQ":qq,"commentContent":content,"commentNickname":nickName}
        $.ajax({
            url: ctx + "web/comment/submitComment",
            type: 'post',
            data: JSON.stringify(jsonContent),
            processData: false,
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
            }
        });








    })


});

