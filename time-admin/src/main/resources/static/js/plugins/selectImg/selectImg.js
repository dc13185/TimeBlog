/**
 * Created by zxm on 2017/5/20.
 */
var selectImgTake = {
    "init":function(divId,maxSelectNumber){
        if(maxSelectNumber==null||maxSelectNumber==""){
            selectImgTake.initSelectEvent(divId,-1);
        }else{
            selectImgTake.initSelectEvent(divId,maxSelectNumber);
        }
    },
    "initSelectEvent":function(divId,maxSelectNumber){
        $("#"+divId+" .item").on("click",function(){
            $(".img_isCheck i").css("display","none");
            $(".item").removeAttr("ischecked");
            $(this).find(".img_isCheck i").css("display","block");
            $(this).attr("ischecked","true");
        });
    },
    "getSelectImgs":function(divId){
        var selectImgDivs = $("#"+divId+" .item[ischecked='true']");
        return selectImgDivs;
    },
    "cancelInit":function(divId){
        $("#"+divId+" .item").off("click");
        $(".img_isCheck i").css("display","none");
        $("#"+divId+" .item").removeAttr("ischecked");
    }
}
