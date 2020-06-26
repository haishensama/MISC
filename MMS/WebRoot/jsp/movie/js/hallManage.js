/**
 * Created by zouyao on 2017/3/6.
 */
var movieManageCtrl = m.controller("hallManagecontroller",function ($rootScope,$scope) {

    /**
     * 设施管理初始化
     */
    $rootScope.hm_init = function () {
        $scope.prevPage = "上一页";
        $scope.nextPage = "下一页";
        $scope.currentPage = 1;
        $scope.totalPage = 1;
        $scope.blurName = "";
        $scope.orderType = "";

        $scope.getHallPageList();
    };
    /**
     * 获取设施分页列表
     */
    $scope.getHallPageList = function(){
        if($scope.currentPage == 0){
         this.currentPage = 1;
         }else{
         this.currentPage = $scope.currentPage;
         }
        $.ajax({
            type:"POST",
            url:"/hall/getHallPageList",
            data:{"currentPage":this.currentPage,"pageSize":5,"blurName":$scope.blurName,"orderType":$scope.orderType},
            contentType:"application/x-www-form-urlencoded",
            dataType:"json",
            success:function(data){
                console.log(data);
                $scope.$apply(function(){
                    if(data.page.list.length == 0 && $scope.currentPage > 1){
                        $scope.currentPage = $scope.currentPage - 1;
                        $scope.getHallPageList();
                    }
                    $scope.hallList = new Array();
                    var obj = {};
                    for(var temp in data.page.list){
                        obj['id'] = data.page.list[temp].id;
                        obj['row'] = data.page.list[temp].row;
                        obj['col'] = data.page.list[temp].col;
                        obj['num'] = data.page.list[temp].num;
                        obj['is_available'] = data.page.list[temp].is_available;
                       
                        $scope.hallList.push(obj);obj = {};
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
                $scope.getHallPageList();
            }
        }else if(obj=="下一页"){
            if($scope.currentPage == 0){
                //nothing to do
            }else if($scope.currentPage == $scope.totalPage){
                alert("当前已经是最后一页！");//其实并不会发生，因为disabled
            }else{
                $scope.currentPage = $scope.currentPage + 1;
                $scope.getHallPageList();
            }
        }
    };
    /**
     * 新增设施
     */
    $scope.addOneHallModal = function(){
        $scope.add_row= "";
        $scope.add_col = "";
        $scope.is_available = "";

        $("#modalid-hall-add").modal("toggle");
    };
    $scope.addOneHall = function(){
        $.ajax({
            type: "POST",
            url: "/hall/addOneHall",
            data: {
                "row": $scope.add_row,
                "col": $scope.add_col,
                "is_available": $scope.add_is_available,
                
            },
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            success: function (data) {
                console.log(data);
                $scope.$apply(function(){
                    $rootScope.justForModalInfomation = "新增影厅成功!";
                    $("#modalid-toastInfo").modal("toggle");
                    $scope.getHallPageList();
                });
            }
        });
    };
    /**
     * 删除
     */
    $scope.deleteOneHallModal = function(item){
        $scope.deleteTemp = item;
        $("#modalid-hall-delete").modal("toggle");
    };
    $scope.deleteOneHall = function(){
        $.ajax({
            type: "POST",
            url: "/hall/deleteOneHall",
            data: {
                "id": $scope.deleteTemp.id
            },
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            success: function (data) {
                console.log(data);
                $scope.$apply(function(){
                    $rootScope.justForModalInfomation = "删除影厅成功!";
                    $("#modalid-toastInfo").modal("toggle");
                    $scope.getHallPageList();
                });
            }
        });
    };
    /**
     * 编辑设施
     */
    $scope.editOneHallModal = function(item){
        $.ajax({
            type: "POST",
            url: "/hall/getOneHall",
            data: {
                "id": item.id
            },
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            success: function (data) {
                console.log(data);
                $scope.$apply(function(){
                    $scope.editMovieList = new Array();
                    var obj = {};
                    //for(var temp in data.page.list){
                    obj['id'] = data.page.list[temp].id;
                    obj['row'] = data.page.list[temp].row;
                    obj['col'] = data.page.list[temp].col;
                    obj['num'] = data.page.list[temp].num;
                    obj['is_available'] = data.page.list[temp].is_available;
                   
                    //obj['calNum'] = obj['usedNum']+obj['repaireNum'];
                    /*var datestr = new Date(parseInt(data.page.list[temp].createTime));
                     var temstr = datestr.getFullYear() + "年" + (parseInt(datestr.getMonth())+1) + "月" + datestr.getDate() + "日"
                     //+ datestr.getHours() + ":" + datestr.getMinutes() + ":" + datestr.getSeconds()
                     ;
                     obj['createTime'] = temstr;	//创建时间*/
                    $scope.editHallList.push(obj);obj = {};
                    /*$scope.numOperation = $scope.editEquipmentList[0].totalNum<($scope.editEquipmentList[0].usedNum+$scope.editEquipmentList[0].repaireNum);
                    console.log($scope.numOperation);*/
                    //}
                });
            }
        });
        $("#modalid-hall-edit").modal("toggle");
    };
    $scope.editOneHall = function(){
        $.ajax({
            type: "POST",
            url: "/hall/editOneHall",
            data: {
                "id": $scope.editMovieList[0].id,
                "row": $scope.editMovieList[0].row,
                "col": $scope.editMovieList[0].col,
                "is_available": $scope.editMovieList[0].is_available
            },
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            success: function (data) {
                console.log(data);
                $scope.$apply(function(){
                    $rootScope.justForModalInfomation = "编辑影厅成功!";
                    $("#modalid-toastInfo").modal("toggle");
                    $scope.getHallPageList();
                });
            }
        });
    };

});
var dateChooseArray = new Array();