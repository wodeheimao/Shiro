app.controller("shiroController",function ($scope,$http) {
    //1.用户登录
    $scope.login=()=>{
        $http.get("./login?username="+$scope.username+"&password="+$scope.password).success(response=>{
            if(response.success){
                location.href = "users/listmenus.html";
            }else{
                alert(response.message);
            }
        })
    }
})