$(document).ready(function(){
    $('.form-horizontal').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            'loginname': {
                message: '用户名无效',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    stringLength: {
                        min: 2,
                        max: 20,
                        message: '用户名长度必须在2到20位之间'
                    },
                },

            },
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
            }
        },
    });
});
