var ajaxUrl = 'users/';
var datatableApi;
var modalForm = $('#editUser');
var successmsg = 'User successfully saved';
var errormsg = 'An user with such email is already registered in the app!!'

$(function () {
    datatableApi = $('#userTable').DataTable(extendsOpts({
        "columns": [
            {
                "data": "lastname"
            },
            {
                "data": "name"
            },
            {
                "data": "email",
                "render": function (data, type, row) {
                    if (type == 'display') {
                        return '<a href="mailto:' + data + '">' + data + '</a>';
                    }
                    return data;
                }
            },
            {
                "data": "phone"
            },
            {
                "data": "company.name"
            },
            {
                "data": "roles[].role"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "columnDefs": [
            {"orderable": false, "targets": [2, 3, 4, 5, 6, 7]}
            , {"width": "15%", "targets": [0, 1, 2]}
            , {"width": "5%", "targets": [6, 7]}
        ]
    }));

    frmDetails.validator().on('submit', function (e) {
        e.preventDefault();
        save();
    });
});

function prepareDelete(id)
{
    var currentRow = $("#rowid" + id).closest("tr");
    var fname = currentRow.find("td:eq(0)").text(); // get current row 1st TD value
    var lname = currentRow.find("td:eq(1)").text(); // get current row 2nd TD
    var email = currentRow.find("td:eq(2)").text(); // get current row 3rd TD
    return "<br />" + fname + " " + lname + " (" + email + ")";
}