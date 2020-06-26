/**
 * Created by zouyao on 2017/3/6.
 */
var nowOnManageCtrl = m.controller("willOnManagecontroller",function ($rootScope,$scope) {

	
	 $scope.scenicSortConstFunc = function ($event) {
	        //console.log($event.target.innerHTML);
	        var temp = $event.target.innerHTML;
	        if(temp == "按时间由近及远"){
	            $scope.sortRuleArray = "00trc0";
	        }else if(temp == "按时间由远及近"){
	            $scope.sortRuleArray = "00tcr0";
	        }else if(temp == "按评分从低到高"){
	            $scope.sortRuleArray = "000ssm";
	        }else if(temp == "按评分从高到低"){
	            $scope.sortRuleArray = "000sms";
	        }else if(temp == "全部"){
	            $scope.sortRuleArray = "000000";
	        }
	        $scope.scenicSortConstw = temp;
	        $scope.getWillOnMoviePageList();
	    };
	    /**
    /**
     * 设施管理初始化
     */
    $rootScope.won_init = function () {
        $scope.prevPage = "上一页";
        $scope.nextPage = "下一页";
        $scope.currentPage = 1;
        $scope.totalPage = 1;
        $scope.blurName = "";
        $scope.orderType = "";

    	$scope.scenicSortConstw = "排序规则";
    	$scope.sortRuleArray = "000000";

        $scope.getWillOnMoviePageList();
    };
    /**
     * 获取设施分页列表
     */
    $scope.getWillOnMoviePageList = function(){
        if($scope.currentPage == 0){
         this.currentPage = 1;
         }else{
         this.currentPage = $scope.currentPage;
         }
        $.ajax({
            type:"POST",
            url:"/movie/getMoviePageList",
            data:{"currentPage":this.currentPage,"pageSize":5,"blurName":$scope.blurName,"sortRuleArray":$scope.sortRuleArray},
            contentType:"application/x-www-form-urlencoded",
            dataType:"json",
            success:function(data){
                console.log(data);
                $scope.$apply(function(){
                    if(data.page.list.length == 0 && $scope.currentPage > 1){
                        $scope.currentPage = $scope.currentPage - 1;
                        $scope.getWillOnMoviePageList();
                    }
                    $scope.willOnmovieList = new Array();
                    var obj = {};
                    for(var temp in data.page.list){
                        obj['id'] = data.page.list[temp].id;
                        obj['name'] = data.page.list[temp].name;
                        obj['director'] = data.page.list[temp].director;
                        obj['actor'] = data.page.list[temp].actor;
                        obj['score'] = data.page.list[temp].score;
                        obj['plot'] = data.page.list[temp].plot;
                        obj['url'] = data.page.list[temp].url;
                        
                        var datestr = new Date(parseInt(data.page.list[temp].premiere));
                        var temstr = datestr.getFullYear() + "年" + (parseInt(datestr.getMonth())+1) + "月" + datestr.getDate() + "日"
                       // + datestr.getHours() + ":" + datestr.getMinutes();// + ":" + datestr.getSeconds();
                        ;
                        obj['premiere'] = temstr;
                        /*var datestr = new Date(parseInt(data.page.list[temp].createTime));
                        var temstr = datestr.getFullYear() + "年" + (parseInt(datestr.getMonth())+1) + "月" + datestr.getDate() + "日"
                        //+ datestr.getHours() + ":" + datestr.getMinutes() + ":" + datestr.getSeconds()
                            ;
                        obj['createTime'] = temstr;	//创建时间*/
                        
                        if(datestr.getDay() >(new Date()).getDay()||datestr.getMonth() >(new Date()).getMonth())
                        {

                            $scope.willOnmovieList.push(obj);
                        	
                        }
                        obj = {};
                    }
                    //分页相关更新
                    $scope.currentPage = data.page.current;
                    $scope.totalPage = data.page.total;
                });
            }
        });
    };
    /**
     * 分页操作
     * @param obj
     */
    $scope.makePagingList = function(obj){
        if(obj=="上一页"){
            if($scope.currentPage == 0){
                //nothing to do
            }else if($scope.currentPage == 1){
                alert("当前已经是第一页！");//其实并不会发生，因为disabled
            }else{
                $scope.currentPage = $scope.currentPage - 1;
                $scope.getWillOnMoviePageList();
            }
        }else if(obj=="下一页"){
            if($scope.currentPage == 0){
                //nothing to do
            }else if($scope.currentPage == $scope.totalPage){
                alert("当前已经是最后一页！");//其实并不会发生，因为disabled
            }else{
                $scope.currentPage = $scope.currentPage + 1;
                $scope.getWillOnMoviePageList();
            }
        }
    };
   

});