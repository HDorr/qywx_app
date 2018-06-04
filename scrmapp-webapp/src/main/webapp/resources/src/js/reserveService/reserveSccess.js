/*
* @Author: Administrator
* @Date:   2017-04-26 15:20:09
* @Last Modified by:   Administrator
* @Last Modified time: 2017-04-26 17:24:10
*/

(function($,window){
    var rootUrl = getRootPath();
    var name = getUrlParam("name") || "获取姓名失败";
    var tel = getUrlParam("tel") || "获取手机失败";
    var address = getUrlParam("address") || "获取地址失败";
	var time = getUrlParam("time") || "获取时间失败";
	var contactsTelephone = getUrlParam("contactsTelephone") || "获取固话失败"

	var str = 	'<p>您的预约信息</p>'+
	            '<p><span class="title">联系信息：</span><span  class="con">'+name+','+tel+','+contactsTelephone+'</span></p>'+
	            '<p><span class="title">服务地址：</span><span  class="con">'+address+'</span></p>'+
	            '<p><span class="title">预约时间：</span><span  class="con">'+time+'</span></p>'

	 $('.reserveInfo').html(str);

	 $('#backToReserveList').click(function(event) {
	 	window.location.href=pageUrls.orderList
	 });

})(jQuery, window)