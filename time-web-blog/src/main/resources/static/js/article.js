function initArticleData(pageNumber,pageSize,ctx) {
    debugger;
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
                       span = "<span class=\"fc-blue\">【原创】</span>";
                    }else{
                        span = "<span class=\"fc-red\">【转载】</span>";
                    }
                    articleModel.find(".title"). prepend(span);

                    if(rows !== null){
                        for (let labelIndex in labels) {
                            let label = labels[labelIndex];
                            let labelName = label.labelName;
                            let labelModel = "<a class=\"tag\">"+labelName+"</a>";
                            articleModel.find(".label-content").append(labelModel);
                        }
                    }

                    articleModel.removeClass("articleModel");
                    articleModel.find(".article-title").text(article.articleTitle).attr("href",ctx+"web/article/toArticleDetails?articleId="+article.articleId);
                    articleModel.find(".day").text(day);
                    articleModel.find(".month").text(month);
                    articleModel.find(".year").text(year);
                    articleModel.find(".article-img").attr("src",article.coverImage);
                    $("#bloglist").append(articleModel);
                }
            }
        }
    });

}


function currentPage(currentPage){
    console.log("当前页码数：" + currentPage);
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
