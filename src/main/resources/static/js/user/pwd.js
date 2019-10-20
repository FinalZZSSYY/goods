$(document).ready(function(){
    $('.form-horizontal').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            'loginpass': {
                validators: {
                    notEmpty: {
                        message: '登录密码不能为空'
                    },
                    stringLength: {
                        min: 3,
                        max: 20,
                        message: '密码长度必须在3到20位之间'
                    },
                }
            },
            'newpass': {
                validators: {
                    notEmpty: {
                        message: '登录密码不能为空'
                    },
                    stringLength: {
                        min: 3,
                        max: 20,
                        message: '密码长度必须在3到20位之间'
                    },
                }
            },
            'reloginpass': {
                validators: {
                    notEmpty: {
                        message: '确认密码不能为空'
                    },
                    stringLength: {
                        min: 3,
                        max: 20,
                        message: '密码长度必须在3到20位之间'
                    },
                }
            }
        },
    });
});
