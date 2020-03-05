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

        layer.open({
            type: 1,
            content: $('#comment-box'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            area: ['300px', '250px'],

            title: ['评论信息', 'font-size:18px;'],
            offset:'200px',
            skin: 'layui-layer-molv'
        });

      /*  layer.msg('你啥都没输入！', {icon: 5,offset:'280px'});*/
    })


});

