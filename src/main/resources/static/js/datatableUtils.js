var frmDetails = $('#detailsForm');
var frmEditUser = $('#editUser');

function deleteRow(id) {
    var currentRow = $("#rowid" + id).closest("tr");
    var fname = currentRow.find("td:eq(0)").text(); // get current row 1st TD value
    var lname = currentRow.find("td:eq(1)").text(); // get current row 2nd TD
    var email = currentRow.find("td:eq(2)").text(); // get current row 3rd TD
    var data = "<br />" + fname + " " + lname + " (" + email + ")";

    bootbox.dialog({
        message: "Are you sure you want to delete user" + data + " ?",
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
                        url: 'users/' + id,
                        /*data: {id: id},*/
                        success: function (data) {
                            $("#rowid" + data).closest("tr").fadeOut(1000, function () {
                                $("#rowid" + data).closest("tr").remove();
                            });
                        }
                    })
                        .done(function (response) {
                            bootbox.alert('User has been deleted successfully...');
                        })
                        .fail(function () {
                            bootbox.alert('Oops... Something Went Wrong ....');
                        })
                }
            }
        }
    });

}

function updateRow(id) {
    frmDetails[0].reset();

    var validator = frmDetails.data('bs.validator');
    if (frmDetails.find('.has-error, .has-danger').length > 0) {
        validator.reset();
        validator.update();
    }

    var ajaxUrl = "users/" + id;
    $.get(ajaxUrl, function (data) {
        $.each(data, function (key, value) {
            frmDetails.find("input[name='" + key + "']").val(value);
        });
        frmEditUser.modal();
    });
}