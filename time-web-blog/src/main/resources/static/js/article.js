function initArticleData(pageNumber,pageSize,ctx) {
    $("#pager").hide();
    $("#bloglist").empty();
    let jsonContent = {"pageNumber": pageNumber, "pageSize": pageSize}
    $.ajax({
        url: ctx + "web/article/showArticleList",
        type: 'post',
        data: JSON.stringify(jsonContent),
        processData: false,
        contentType: false,
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        },
        success: function (msg) {
            if (msg.code == 200) {
                let rows = msg.rows;
                for (let index in rows) {
                    let article = rows[index];
                    let articleModel = $(".articleModel").clone();
                    let updateTime = new Date(article.updateTime);
                    let  year = updateTime.format("yyyy");
                    let  day = updateTime.format("dd");
                    let  month = updateTime.format("MM");
                    let labels = article.labelList;
                    let span;
                    //1为原创 0为转载
                    if(article.isOriginal == 1){
                       span = "<span class=\"fc-original\">【原创】</span>";
                    }else{
                        span = "<span class=\"fc-reproduced\">【转载】</span>";
                    }
                    articleModel.find(".title").prepend(span);

                    if(rows !== null){
                        for (let labelIndex in labels) {
                            let label = labels[labelIndex];
                            let labelName = label.labelName;
                            let labelModel = "<a class=\"tag\">"+labelName+"</a>";
                            articleModel.find(".label-content").append(labelModel);
                        }
                    }
                    //浏览
                    let accessCount = article.accessCount;
                    let likeCount = article.likeCount;
                    if (article.accessCount == null){
                        accessCount = 0;
                    }
                    if (article.likeCount == null){
                        likeCount = 0;
                    }
                    //置顶
                    let isTop = article.isTop;
                    if(isTop == 1){
                         let topDiv = "<div class=\"fc-flag\">置顶</div>";
                        articleModel.prepend(topDiv)
                    }

                    //图片
                    if(article.coverImage.indexOf("img-blank") == -1){
                        articleModel.find(".article-img").attr("src",article.coverImage);
                    }else{
                        articleModel.find(".article-img").remove();
                    }
                    articleModel.removeClass("articleModel");
                    articleModel.find(".article-title").text(article.articleTitle).attr("href",ctx+"web/article/toArticleDetails?articleId="+article.articleId);
                    articleModel.find(".day").text(day);
                    articleModel.find(".month").text(month);
                    articleModel.find(".year").text(year);
                    articleModel.find(".accessCount").text(accessCount);
                    articleModel.find(".likeCount").text(likeCount);
                    $("#bloglist").append(articleModel);
                    $("#pager").show();
                }
            }
        }
    });

}


function currentPage(currentPage){
        initArticleData(currentPage,5,ctx);
         $(".layui-fixbar-top").trigger("click");
}

function initPage(total){
    $("#pager").zPager({
        totalData: total,
        htmlBox: $('#wraper'),
        btnShow: true,
        ajaxSetData: false
    });
}


Date.prototype.format = function(fmt) {
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt)) {
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
}
