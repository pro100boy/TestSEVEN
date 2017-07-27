var frmDetailsArr = [$('#detailsFormUser'), $('#detailsFormCmp'), $('#detailsFormRep')];
var modalFormArr = [$('#editUser'), $('#editCompany'), $('#editReport')];

/* ============================================================ */
/* http://stepansuvorov.com/blog/2014/04/jquery-put-and-delete  */
/* provide PUT/DELETE ability via jQuery                        */
/* ============================================================ */
jQuery.each(["put", "delete"], function (i, method) {
    jQuery[method] = function (url, data, callback, type) {
        if (jQuery.isFunction(data)) {
            type = type || callback;
            callback = data;
            data = undefined;
        }

        return jQuery.ajax({
            url: url,
            type: method,
            dataType: type,
            data: data,
            success: callback
        });
    };
});

function makeEditable() {
    //form = $('#detailsForm');
    // $(document).ajaxError(function (event, jqXHR, options, jsExc) {
    //     failNoty(event, jqXHR, options, jsExc);
    // });

    // var token = $("meta[name='_csrf']").attr("content");
    // var header = $("meta[name='_csrf_header']").attr("content");
    // $(document).ajaxSend(function(e, xhr, options) {
    //     xhr.setRequestHeader(header, token);
    // });
}

// TODO передавать нужные параметры в параметрах функций
function save(frmDetails, modalForm, successmsg, datatableApi) {
    var validator = frmDetails.data('bs.validator');

    validator.reset();
    validator.update();
    validator.validate();

    // validate passed
    if (!validator.hasErrors()) {
        $.ajax({
            type: "POST",
            url: datatableApi.ajax.url(),
            data: frmDetails.serialize(),
            success: function (data) {
                modalForm.modal('hide');
                bootbox.alert({
                    message: successmsg,
                    size: 'small',
                    callback: function () {
                        datatableApi.ajax.reload();
                    }
                });
            },
            error: function (jqXHR, textStatus, thrownError) {
                // doing SUBSTRING instead of REPLACE because last quotes doesn't change due to <br/>
                var l = jqXHR.responseText.length - 1;
                var msg = jqXHR.responseText.substring(1, l);
                bootbox.alert({
                    message: (msg),
                    size: 'small'
                });
            }
        });
    }
}

function formatErrorMessage(jqXHR, thrownError) {
    if (jqXHR.status === 0) {
        return ('Not connected.\nPlease verify your network connection.');
    } else if (jqXHR.status == 404) {
        return ('The requested page not found. [404]');
    } else if (jqXHR.status == 500) {
        return ('Internal Server Error [500].');
    } else if (thrownError === 'parsererror') {
        return ('Requested JSON parse failed.');
    } else if (thrownError === 'timeout') {
        return ('Time out error.');
    } else if (thrownError === 'abort') {
        return ('Ajax request aborted.');
    } else if (thrownError === 'error') {
        return ('Error occurred.');
    } else {
        return ('Uncaught Error.<br/>' + jqXHR.responseText);
    }
}

function updateRow(id) {
    var currentTableId = $("#rowid" + id).closest("table").attr("id");
    var frmDetails;
    var ajaxUrl;
    var modalForm;
    switch (currentTableId){
        case 'userTable':
            ajaxUrl = datatableApiUsers.ajax.url();
            frmDetails = frmDetailsArr[0];
            modalForm = modalFormArr[0];
            break;
        case 'companyTable':
            ajaxUrl = datatableApiCmp.ajax.url();
            frmDetails = frmDetailsArr[1];
            modalForm = modalFormArr[1];
            break;
        //TODO доделать
        case 'reportTable': /*ajaxUrl = datatableApiRep.ajax.url();*/ break;
    }

    frmDetails[0].reset();

    var validator = frmDetails.data('bs.validator');
    if (frmDetails.find('.has-error, .has-danger').length > 0) {
        validator.reset();
        validator.update();
    }

    $.get(ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            frmDetails.find("input[name='" + key + "']").val(value);
        });
        modalForm.modal();
    });

    if (frmDetails == frmDetailsArr[0])
        getCompanies($('#dropOperator'));
}

function myValidate(frmDetails) {
    frmDetails.find(':input').val('');
    // get validator and reset it
    frmDetails.data('bs.validator').reset();

    if (frmDetails == frmDetailsArr[0])
        getCompanies($('#dropOperator'));
}

function renderEditBtn(data, type, row) {
    if (type == 'display') {
        return '<button class="btn btn-xs btn-primary" id="rowid' + row.id + '" onclick="updateRow(' + row.id + ');">' +
            '<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>';
    }
}

function renderDeleteBtn(data, type, row) {
    if (type == 'display') {
        return '<button class="btn btn-xs btn-danger" id="rowid' + row.id + '" onclick="deleteRow(' + row.id + ');">' +
            '<span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button>';
    }
}

function deleteRow(id) {
    var currentTableId = $("#rowid" + id).closest("table").attr("id");
    var ajaxUrl;
    switch (currentTableId){
        case 'userTable': ajaxUrl = datatableApiUsers.ajax.url(); break;
        case 'companyTable': ajaxUrl = datatableApiCmp.ajax.url(); break;
        // TODO доделать
        //case 'reportTable': ajaxUrl = datatableApiRep.ajax.url(); break;
    }

    var currentRow = $("#rowid" + id).closest("tr");
    var cell1 = currentRow.find("td:eq(0)").text(); // get current row 1st TD value
    var cell2 = currentRow.find("td:eq(1)").text(); // get current row 2nd TD
    var res = "<br />" + cell1 + " " + cell2;

    bootbox.dialog({
        message: "Are you sure you want to delete" + res + " ?",
        title: "<i class='glyphicon glyphicon-trash'></i> Delete !",
        size: 'small',
        buttons: {
            success: {
                label: "No",
                className: "btn-success",
                callback: function () {
                    $('.bootbox').modal('hide');
                }
            },
            danger: {
                label: "Delete!",
                className: "btn-danger",
                callback: function () {

                    $.ajax({
                        type: 'DELETE',
                        url: ajaxUrl + id,
                        success: function (data) {
                            $("#rowid" + data).closest("tr").fadeOut(1000, function () {
                                $("#rowid" + data).closest("tr").remove();
                            });
                            if (currentTableId === 'companyTable') {
                                // TODO удаляем связанных с компанией юзеров и отчеты
                                datatableApiUsers.ajax.reload();
                                //datatableApiRep.ajax.reload();
                            }
                        }
                    })
                        .done(function (response) {
                            bootbox.alert('Item has been deleted successfully...');
                        })
                        .fail(function () {
                            bootbox.alert('Oops... Something Went Wrong ....');
                        })
                }
            }
        }
    });
}