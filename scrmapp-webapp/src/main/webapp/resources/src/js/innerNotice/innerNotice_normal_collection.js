/**
 * Created by Administrator on 2017/4/17 0017.
 */
var glData = {
        userId:$("#userIdInput").val(),
        noticeId:$("#noticeIdInput").val()
    }

;(function($,window){
    $(".starIcon").click(function(e){
        var $that = $(this);
       $.ajax({
           url:"/scrmapp/qyhNotice/noticeCollection",
           type:"POST",
           data:{
               userId:glData.userId,
               noticeId:glData.noticeId
           },
           success:function(data){
               if(data.errorCode!=1){
                   $that.toggleClass("active");
               }else{
                   alert("错误："+data.msg);
               }
           }
       })
    })
})(jQuery,window);