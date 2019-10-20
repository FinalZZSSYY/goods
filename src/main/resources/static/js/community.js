function post() {
    var questionId = $("#questionId").val();
    var commentContent = $("#commentContent").val();
    if (!commentContent){
        alert("不能回复空内容");
        return;
    }
    $.ajax({
        url:"/comment", //请求路径
        type:"post", //请求类型get,post
        contentType:"application/json;charset=UTF-8",
        dataType:"json",
        //用json传输比较快
        data:JSON.stringify({
            "parentId":questionId,
            "content":commentContent,
            "type":1
        }),
        success:function(data){ //请求成功后的事件
            if (data.code == 200){
                $("#commentContent").val("");
                window.location.reload();
            }else{
                if(data.code == 2003){
                    //确认框
                    var isAccept = confirm(data.message);
                    if (isAccept){
                        window.open("https://github.com/login/oauth/authorize?client_id=41def0a76d504f565dd2&redirect_uri=http://localhost:8887/callback&scope=user&state=1")
                        window.localStorage.setItem("closable",true);
                    }
                }else{
                    alert(data.message);
                }
            }
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){ //错误时的事件

        }
    });
}

function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+ id);

    var collapse = e.getAttribute("data-collapse");
    if (collapse){
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        comments.addClass("in");
        e.setAttribute("data-collapse","in");
        e.classList.add("active");
    }
    
}