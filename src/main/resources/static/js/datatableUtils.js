var frmDetailsArr = [$('#detailsFormUser'), $('#detailsFormCmp'), $('#detailsFormRep')];
var modalFormArr = [$('#editUser'), $('#editCompany'), $('#editReport')];
var cmpID;
// https://api.jquery.com/jquery.extend/#jQuery-extend-deep-target-object1-objectN
// common options for datatables
function extendsOpts(ajaxUrl, opts) {
    $.extend(true, opts,
        {
            "ajax": {
                "url": ajaxUrl,
                "dataSrc": ""
            },
            "autoWidth": false,
            "ordering": true,
            "paging": true,
            "info": true,
            "language": {
                "url" : "json/" + localeCode + ".json"
            },
            "initComplete": makeEditable
        }
    );
    return opts;
}

function formatDate(date) {
    return date.replace('T', ' ').substr(0, 16);
}

function makeEditable() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
}

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
            data: frmDetails.serialize()
        })
            .done(function (data, textStatus, jqXHR) {
                modalForm.modal('hide');
                bootbox.alert({
                    message: successmsg,
                    size: 'small',
                    callback: function () {
                        datatableApi.ajax.reload();
                        if (modalForm == modalFormArr[1]) {
                            // Перегружаем, чтобы появился созданный юзер после сохранения компании
                            datatableApiUsers.ajax.reload();
                        }
                    }
                })
            })
            .fail(function (jqXHR, textStatus, thrownError) {
                showErrorMessage(jqXHR);
            })
    }
}

// заполняем поля в модальной форме при редактировании 
function updateRow(id) {
    var currentTableId = $("#rowid" + id).closest("table").attr("id");
    var frmDetails;
    var ajaxUrl;
    var modalForm;

    switch (currentTableId) {
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
        case 'reportTable':
            ajaxUrl = datatableApiReport.ajax.url();
            frmDetails = frmDetailsArr[2];
            modalForm = modalFormArr[2];
            break;
    }

    frmDetails[0].reset();

    var validator = frmDetails.data('bs.validator');
    if (frmDetails.find('.has-error, .has-danger').length > 0) {
        validator.reset();
        validator.update();
    }

    $.get(ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
            frmDetails.find("input[name='" + key + "']").val(
                key === "date" ? formatDate(value) : value
            );
            // просмотр отчета
            frmDetails.find("textarea[name='" + key + "']").val(value);
            // company id
            cmpID = (key === "company") ? value.id : cmpID = undefined;
        });
        modalForm.modal();
    }).fail(function (jqXHR, textStatus, thrownError) {
        showErrorMessage(jqXHR);
    });

    if (frmDetails === frmDetailsArr[0])
        getCompanies($('#dropOperator'), cmpID);
}

function myValidate(frmDetails) {
    frmDetails.find(':input').val('');
    // get validator and reset it
    frmDetails.data('bs.validator').reset();

    // если редактируем юзера, то подгружаем ему список компаний
    if (frmDetails === frmDetailsArr[0])
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
    switch (currentTableId) {
        case 'userTable':
            ajaxUrl = datatableApiUsers.ajax.url();
            break;
        case 'companyTable':
            ajaxUrl = datatableApiCmp.ajax.url();
            break;
        case 'reportTable':
            ajaxUrl = datatableApiReport.ajax.url();
            break;
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
                        url: ajaxUrl + id
                    })
                        .done(function (data, textStatus, jqXHR) {
                            $("#rowid" + data).closest("tr").fadeOut("slow", function () {
                                $("#rowid" + data).closest("tr").remove();
                            });
                            if (currentTableId === 'companyTable') {
                                // связанные с компанией юзеры и отчеты удалились автоматически. Перегружаем
                                datatableApiUsers.ajax.reload();
                                datatableApiReport.ajax.reload();
                            }
                            bootbox.alert({
                                message: 'Item has been deleted successfully',
                                size: 'small'
                            })
                        })
                        .fail(function (jqXHR, textStatus, thrownError) {
                            showErrorMessage(jqXHR);
                        })
                }
            }
        }
    });
}

function showErrorMessage(jqXHR) {
    // doing SUBSTRING instead of REPLACE because last quotes doesn't change due to <br/>
    var l = jqXHR.responseText.length - 1;
    var msg = jqXHR.responseText.substring(1, l);
    bootbox.alert({
        message: msg,
        size: 'small'
    })
}