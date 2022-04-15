window.onload = function () {
    document.getElementById("form").onsubmit = function () {
        return checkAge() && checkNumber() && checkPhone();
    };

    document.getElementById("age").onblur = checkAge;
    document.getElementById("number").onblur = checkNumber;
    document.getElementById("phone").onblur = checkPhone;
};

function checkAge() {
    //获取文本框填写内容
    var a_age = document.getElementById("age").value;
    //定义正则表达式
    var reg_age = /^((1[1-6])|[1-9])?\d$/;
    //判断输入的值是否符合正则表达式
    var flag = reg_age.test(a_age);
    var s_age = document.getElementById("s_age");
    if (!flag) {
        //错误提示红色字体信息
        s_age.innerHTML = "年龄格式输入有误! "
    }
    return flag;
}

function checkNumber() {
    //获取文本框填写内容
    var a_number = document.getElementById("number").value;
    //定义正则表达式
    var reg_number = /^\d{15}|\d{18}/;
    //判断输入的值是否符合正则表达式
    var flag = reg_number.test(a_number);
    var s_number = document.getElementById("s_number");
    if (!flag) {
        //错误提示红色字体信息
        s_number.innerHTML = "身份证号格式有误! "
    }
    return flag;

}

function checkPhone() {
    //获取文本框填写内容
    var a_phone = document.getElementById("phone").value;
    //定义正则表达式
    var reg_phone = /^\d{11}|\d{11}/;
    //判断输入的值是否符合正则表达式
    var flag = reg_phone.test(a_phone);
    var s_phone = document.getElementById("s_phone");
    if (!flag) {
        //错误提示红色字体信息
        s_phone.innerHTML = "号码格式有误! "
    }
    return flag;

}