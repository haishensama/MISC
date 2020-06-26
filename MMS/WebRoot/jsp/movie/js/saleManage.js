/**
 * Created by zouyao on 2017/3/6.
 */
var myOrderCtrl = m.controller("salecontroller",function ($rootScope,$scope) {
   

    /**
     * 初始化
     */
    $rootScope.si_init = function () {
       
        $scope.blurUserName = "";
        $scope.prevPage = "上一页";
        $scope.nextPage = "下一页";
        $scope.currentPage = 1;
        $scope.totalPage = 1;
        $scope.constRef = [["查看详情"]];
        $scope.blurUserName = "";
        $scope.orderType = "";
        
        
        $scope.getSaleList();
        //$scope.fortest();
    };
    /**
     * 获取预约分页列表
     */
    $scope.getSaleList = function(){
        if($scope.currentPage == 0){
         this.currentPage = 1;
         }else{
         this.currentPage = $scope.currentPage;
         }
        $.ajax({
            type:"POST",
            url:"/arrange/getArrangePageList",
            data:{"currentPage":this.currentPage,"pageSize":5},
            contentType:"application/x-www-form-urlencoded",
            dataType:"json",
            success:function(data){
                console.log(data);
                $scope.$apply(function(){
                    if(data.page.list.length == 0 && $scope.currentPage > 1){
                        $scope.currentPage = $scope.currentPage - 1;
                        $scope.getSaleList();
                    }
                    $scope.orderList = new Array();
                    var obj = {};
                    for(var temp in data.page.list){
                        obj['id'] = data.page.list[temp].id;
                        obj['price'] = data.page.list[temp].price;
                        obj['movie'] = data.page.list[temp].movie.name;
                        obj['hall'] = data.page.list[temp].hall.id;
                        obj['salenum'] = data.num[data.page.list[temp].id];
                        var datestr = new Date(parseInt(data.page.list[temp].startTime));
                        var temstr = datestr.getFullYear() + "年" + (parseInt(datestr.getMonth())+1) + "月" + datestr.getDate() + "日"
                        + datestr.getHours() + ":" + datestr.getMinutes() //+ ":" + datestr.getSeconds()
                            ;
                        obj['startTime'] = temstr;	//预约时间
                        
                        var datestr = new Date(parseInt(data.page.list[temp].endTime));
                        var temstr = datestr.getFullYear() + "年" + (parseInt(datestr.getMonth())+1) + "月" + datestr.getDate() + "日"
                        + datestr.getHours() + ":" + datestr.getMinutes()// + ":" + datestr.getSeconds()
                            ;
                        obj['endTime'] = temstr;	//预约时间
                        
                        
                        $scope.orderList.push(obj);
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
                $scope.getSaleList();
            }
        }else if(obj=="下一页"){
            if($scope.currentPage == 0){
                //nothing to do
            }else if($scope.currentPage == $scope.totalPage){
                alert("当前已经是最后一页！");//其实并不会发生，因为disabled
            }else{
                $scope.currentPage = $scope.currentPage + 1;
                $scope.getSaleList();
            }
        }
    };
    /**
     * 查看场地详情
     */
    $scope.viewDetailOfArrange = function(obj){
        $scope.viewDetailOfArrange = obj;
        $("#modalid-arrange-view").modal("toggle");
    };
    /**
     * 删除预约
     */
    $scope.deleteOneOrderModal = function(item){
        $scope.deleteTemp = item;
        $("#modalid-arrange-delete").modal("toggle");
    };
    $scope.deleteOneOrder = function(){
        $.ajax({
            type: "POST",
            url: "/arrange/deleteOneArrange",
            data: {
                "id": $scope.deleteTemp.id
            },
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            success: function (data) {
                console.log(data);
                $scope.$apply(function(){
                    $rootScope.justForModalInfomation = "删除成功!";
                    $("#modalid-toastInfo").modal("toggle");
                    $scope.getSaleList();
                });
            }
        });
    };

});