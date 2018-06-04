/*
* @Author: Administrator
* @Date:   2017-04-19 15:16:03
* @Last Modified by:   Administrator
* @Last Modified time: 2017-04-19 17:44:06
*/

var glData = {
    errorCode:$("#errorCode").val(),
    msg:$("#msg").val()
}


;(function($,window){
        if (glData.errorCode==40001) {
            $('.warningTxt').text('你还不是该企业员工！')
        }
        else if (glData.errorCode==40002) {
            $('.warningTxt').text('您还未关注该企业号！')
        	$('.qrCode').css('display', 'none');
        }
        else if (glData.errorCode==40003) {
            $('.warningTxt').text('您在该企业号已经被禁用！');
        	$('.qrCode').css('display', 'none');
        }
        else if (glData.errorCode==40004) {
            $('.warningTxt').text('该公告已经被删除！')
        }
        else if (glData.errorCode==40005) {
            $('.warningTxt').text('你还没有权限查看该公告！')
        }
        else if (glData.errorCode==40006) {
            $('.warningTxt').text('该公告不存在！')
        }

})(jQuery,window);