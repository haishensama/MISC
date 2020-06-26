/**
 * Created by zouyao on 2017/3/6.
 */
var addOrderCtrl = m.controller("addOrdercontroller",function ($rootScope,$scope) {

    /**
     * 新增预约初始化
     */
    $rootScope.ao_init = function () {
        $scope.prevPage = "上一页";
        $scope.nextPage = "下一页";
        $scope.currentPage = 1;
        $scope.totalPage = 1;
        $scope.blurName = "";
        $scope.orderType = "";
        $scope.arrange_id = 0;//addorderto
        $scope.row = [1,2,3,4,5,6,7,8,9,10];
        $scope.col = [1,2,3,4,5,6,7,8,9,10];
        $scope.rownum = 0;
        $scope.colnum = 0;
        $scope.idOfArrange = [];
        $scope.num = [];
        $scope.getArrangelistOfNowOn();
    };
    
    $scope.addOneMovieorder = function(item){
    	
    	 $scope.col = [1,2,3,4,5,6,7,8,9,10];
       	 for(var temp in $scope.oneArrangelistOfNowOnList){
        	//console.log($scope.oneArrangelistOfNowOnList[temp].seat); 
       		 if(item == parseInt($scope.oneArrangelistOfNowOnList[temp].seat/10)+1){
        			 for(var i=0;i<$scope.col.length;i++){
        				 if(i != $scope.col.indexOf($scope.oneArrangelistOfNowOnList[temp].seat%10)){
        					 
        						if($scope.col[i] !=0){
        							
        							$scope.col[i] = $scope.row[i];
        						} 
        					 
        				 }else{
        					 $scope.col[i] = 0;
        					 
        				 }
        			 }
        		 }
        	 }
       	 var coltemp = [];
       	 var j = 0 ;
       	 for(var i=0;i<$scope.col.length;i++){
       		  if($scope.col[i] != 0){
       			 coltemp[j++] = $scope.col[i];
       		 }
       	}
       	$scope.col = coltemp;
    	
    	$scope.arrange_id = item.id;
    	console.log($scope.arrange_id)
    	$.ajax({
            type:"POST",
            url:"/sale/getSalePageByArrange",
            data:{"currentPage":this.currentPage,"pageSize":100,"arrange_id":item.id},
            contentType:"application/x-www-form-urlencoded",
            dataType:"json",
            success:function(data){
                console.log(data);
                $scope.$apply(function(){
                     $scope.oneArrangelistOfNowOnList = new Array();
                    var obj = {};
                    //$scope.num = data.page.record;
                    for(var temp in data.page.list){
                        obj['id'] = data.page.list[temp].id;
                        obj['user'] = data.page.list[temp].user;
                        obj['arrange'] = data.page.list[temp].arrange;
                        obj['seat'] = data.page.list[temp].seat;
                        
                        $scope.oneArrangelistOfNowOnList.push(obj);
                        obj = {};   
                    }
                })}
            
    	});
    
    	
    	 $("#modalid-orderOfOneArrange-add").modal("toggle");
    
    }
    
    /**
     * 获取场地分页列表
     */
    $scope.getArrangelistOfNowOn = function(){
        if($scope.currentPage == 0){
            this.currentPage = 1;
        }else{
            this.currentPage = $scope.currentPage;
        }
        $.ajax({
            type:"POST",
            url:"/arrange/getArrangePageListByMovie",
            data:{"currentPage":this.currentPage,"pageSize":100,"movie_id":$rootScope.orderInfo.id},
            contentType:"application/x-www-form-urlencoded",
            dataType:"json",
            success:function(data){
                console.log(data);
                $scope.$apply(function(){
                    if(data.page.list.length == 0 && $scope.currentPage > 1){
                        $scope.currentPage = $scope.currentPage - 1;
                        $scope.getArrangelistOfNowOn();
                    }
                    $scope.ArrangelistOfNowOnList = new Array();
                    var obj = {};
                    for(var temp in data.page.list){
                        $scope.idOfArrange[temp] = data.page.list[temp].id;
                    	obj['id'] = data.page.list[temp].id;
                        obj['movie'] = data.page.list[temp].movie;
                        obj['hall'] = data.page.list[temp].hall;
                        obj['price'] = data.page.list[temp].price;

                        obj['num'] = data.page.list[temp].hall.num - data.num[data.page.list[temp].id];
                        /*var datestr = new Date(parseInt(data.page.list[temp].createTime));
                         var temstr = datestr.getFullYear() + "年" + (parseInt(datestr.getMonth())+1) + "月" + datestr.getDate() + "日"
                         //+ datestr.getHours() + ":" + datestr.getMinutes() + ":" + datestr.getSeconds()
                         ;
                         obj['createTime'] = temstr;	//创建时间*/
                        
                        var datestr = new Date(parseInt(data.page.list[temp].startTime));
                        var temstr = datestr.getFullYear() + "年" + (parseInt(datestr.getMonth())+1) + "月" + datestr.getDate() + "日"
                        + datestr.getHours() + ":" + datestr.getMinutes()// + ":" + datestr.getSeconds()
                        ;
                        obj['startTime'] = temstr;	//创建时间
                        
                        var datestr = new Date(parseInt(data.page.list[temp].endTime));
                        var temstr = datestr.getFullYear() + "年" + (parseInt(datestr.getMonth())+1) + "月" + datestr.getDate() + "日"
                        + datestr.getHours() + ":" + datestr.getMinutes()// + ":" + datestr.getSeconds()
                        ;
                        obj['endTime'] = temstr;	//创建时间
                        
                        //console.log(data.page.list[temp].id);
                        
                        
                        
                        $scope.ArrangelistOfNowOnList.push(obj);
                         obj = {};
                    }
                   
                    //分页相关更新
                    $scope.currentPage = data.page.current;
                    $scope.totalPage = data.page.total;
                    
                });
            }
        });
        
        }
    
    $scope.chooseOneCol = function(item){
    	$scope.colnum = item;
    }
    
    
    $scope.chooseOneRow = function(item){
    	$scope.rownum = item;
        $scope.colnum = 0;

        $scope.col = [1,2,3,4,5,6,7,8,9,10];
   	 for(var temp in $scope.oneArrangelistOfNowOnList){
    	//console.log($scope.oneArrangelistOfNowOnList[temp].seat); 
   		 if(item == parseInt($scope.oneArrangelistOfNowOnList[temp].seat/10)+1){
    			 for(var i=0;i<$scope.col.length;i++){
    				 if(i != $scope.col.indexOf($scope.oneArrangelistOfNowOnList[temp].seat%10)){
    					 
    						if($scope.col[i] !=0){
    							
    							$scope.col[i] = $scope.row[i];
    						} 
    					 
    				 }else{
    					 $scope.col[i] = 0;
    					 
    				 }
    			 }
    		 }
    	 }
   	 var coltemp = [];
   	 var j = 0 ;
   	 for(var i=0;i<$scope.col.length;i++){
   		  if($scope.col[i] != 0){
   			 coltemp[j++] = $scope.col[i];
   		 }
   	}
   	$scope.col = coltemp;
    }
    
    $scope.addToOrderModal = function(){
    	$("#modalid-pay-show").modal("toggle");
    }
    
    /**
     * 提交
     */
    $scope.addToOrder = function(){
        $.ajax({
            type:"POST",
            url:"/sale/addOneSale",
            data:{
                "user_id":$rootScope.idOfLoger,
                "arrange_id":$scope.arrange_id,
                "seat":($scope.rownum -1)*10+$scope.colnum
            },
            contentType:"application/x-www-form-urlencoded",
            dataType:"json",
            success:function(data){
                console.log(data);
                $scope.$apply(function(){
                    $rootScope.justForModalInfomation = "购票成功!";
                    $("#modalid-toastInfo").modal("toggle");
                });
            }
        });
    };

});
