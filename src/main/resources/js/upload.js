$(document).ready(function(){
    init();
});

function init(){

    // $("#emailConfigSave").onclick(function(){
    //     saveConfig();
    // });
    // var a = $("#emailConfigSave");
    // console.log(a);
}

function saveConfig(){
    var emailConfigParam = $("#emailConfigForm").serialize();
    console.log(emailConfigParam);
    var form = $("#emailConfigForm");
    console.log(form);
    var emailConfigParam = new FormData(document.getElementById("emailConfigForm"));
    console.log(emailConfigParam);
    $.ajax({
        type : "post",
        url : "../sendemail",
        data :  emailConfigParam,
        async : false,
        //文件上传配置。
        cache: false,
        processData: false,
        contentType: false,
        success : function(data){
            alert(data);
            // console.log(data);
        }
    });
}