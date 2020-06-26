/**
 * Created by zouyao on 2017/3/6.
 */
var movieManageCtrl = m.controller("movieManagecontroller",function ($rootScope,$scope) {

	
	/*
	 * 上传图片
     * @type {boolean}
     */
    $scope.ifshowpic = false;
    $scope.doUpload = function () {
        var formData = new FormData($( "#uploadForm" )[0]);
        var i = formData.entries();

        console.log(i.next()); // {done:false, value:["k1", "v1"]}
        console.log(i.next()); // {done:false, value:["k1", "v1"]}
        $.ajax({
            url: '/movie/addOneMovie_pic' ,
            type: 'POST',
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (returndata) {
                console.log(returndata);
                console.log(pathshowpic);
                $("#imgid-picPreview").attr("src",pathshowpic);
                $scope.addMovie_picture = pathshowpic;
                $scope.ifshowpic = true;
            },
            error: function (returndata) {
                console.log(returndata);
                $scope.ifshowpic = false;
            }
        });
    };
    
    
    var showPreview = function (obj) {
        pathshowpic = //$("#imgid-picPreview").attr("title")+
            "/movie-imgs/"+
            obj.value.substring(obj.value.lastIndexOf("\\")+1);
        //console.log($("#imgid-picPreview").attr("title"));
    };
    /**
     * 设施管理初始化
     */
    $rootScope.mv_init = function () {
        $scope.prevPage = "上一页";
        $scope.nextPage = "下一页";
        $scope.currentPage = 1;
        $scope.totalPage = 1;
        $scope.blurName = "";
        $scope.sortRuleArray = "000000";

        $scope.getMoviePageList();
    };
    /**
     * 获取设施分页列表
     */
    $scope.getMoviePageList = function(){
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
                        $scope.getMoviePageList();
                    }
                    $scope.movieList = new Array();
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
                        $scope.movieList.push(obj);obj = {};
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
                $scope.getMoviePageList();
            }
        }else if(obj=="下一页"){
            if($scope.currentPage == 0){
                //nothing to do
            }else if($scope.currentPage == $scope.totalPage){
                alert("当前已经是最后一页！");//其实并不会发生，因为disabled
            }else{
                $scope.currentPage = $scope.currentPage + 1;
                $scope.getMoviePageList();
            }
        }
    };
    /**
     * 新增设施
     */
    /*$scope.addOneEquipmentModal = function(){
        $scope.add_equipmentName = "";
        $scope.add_price = "";
        $scope.add_description = "";
        $scope.add_totalNum = "";

        $("#modalid-eqmng-add").modal("toggle");
    };
    $scope.addOneEquipment = function(){
        $.ajax({
            type: "POST",
            url: "/equipment/addToEquipment",
            data: {
                "equipmentName": $scope.add_equipmentName,
                "price": $scope.add_price,
                "description": $scope.add_description,
                "totalNum": $scope.add_totalNum,
                "usedNum": 0,
                "repaireNum": 0
            },
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            success: function (data) {
                console.log(data);
                $scope.$apply(function(){
                    $rootScope.justForModalInfomation = "新增设施成功!";
                    $("#modalid-toastInfo").modal("toggle");
                    $scope.getEquipmentInfoListOfEquipment();
                });
            }
        });
    };*/
    /**
     * 删除设施
     */
    $scope.deleteOneMovieModal = function(item){
        $scope.deleteTemp = item;
        $("#modalid-mvmng-delete").modal("toggle");
    };
    $scope.deleteOneMovie = function(){
        $.ajax({
            type: "POST",
            url: "/movie/deleteOneMovie",
            data: {
                "id": $scope.deleteTemp.id
            },
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            success: function (data) {
                console.log(data);
                $scope.$apply(function(){
                    $rootScope.justForModalInfomation = "删除电影成功!";
                    $("#modalid-toastInfo").modal("toggle");
                    $scope.getMoviePageList();
                });
            }
        });
    };
    /**
     * 编辑设施
     */
    $scope.editOneMovieModal = function(item){
        $.ajax({
            type: "POST",
            url: "/movie/getOneMovie",
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
                    obj['id'] = data.value.value.id;
                    obj['name'] = data.value.value.name;
                    obj['director'] = data.value.value.director;
                    obj['actor'] = data.value.value.actor;
                    obj['score'] = data.value.value.score;
                    obj['plot'] = data.value.value.plot;
                    obj['premiere'] = data.value.value.premiere;
                    //obj['calNum'] = obj['usedNum']+obj['repaireNum'];
                    /*var datestr = new Date(parseInt(data.page.list[temp].createTime));
                     var temstr = datestr.getFullYear() + "年" + (parseInt(datestr.getMonth())+1) + "月" + datestr.getDate() + "日"
                     //+ datestr.getHours() + ":" + datestr.getMinutes() + ":" + datestr.getSeconds()
                     ;
                     obj['createTime'] = temstr;	//创建时间*/
                    $scope.editMovieList.push(obj);obj = {};
                    /*$scope.numOperation = $scope.editEquipmentList[0].totalNum<($scope.editEquipmentList[0].usedNum+$scope.editEquipmentList[0].repaireNum);
                    console.log($scope.numOperation);*/
                    //}
                });
            }
        });
        $("#modalid-mvmng-edit").modal("toggle");
    };
    $scope.editOneMovie = function(){
        $.ajax({
            type: "POST",
            url: "/movie/editOneMovie",
            data: {
                "id": $scope.editMovieList[0].id,
                "name": $scope.editMovieList[0].name,
                "director": $scope.editMovieList[0].director,
                "actor": $scope.editMovieList[0].actor,
                "score": $scope.editMovieList[0].score,
                "plot": $scope.editMovieList[0].plot,
                "premiere": dateChooseArray[0],
        		"url":$scope.addMovie_picture
            },
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            success: function (data) {
                console.log(data);
                $scope.$apply(function(){
                    $rootScope.justForModalInfomation = "编辑电影成功!";
                    $("#modalid-toastInfo").modal("toggle");
                    $scope.getMoviePageList();
                });
            }
        });
    };

});
var dateChooseArray = new Array();