var datatableApiCmp;

$(function () {
    var ajaxUrl = 'companies/';
    var modalForm = $('#editCompany');
    var successmsg = 'Company successfully saved';
    var errormsg = 'A company with such name and email is already registered in the app!!';

    datatableApiCmp = $('#companyTable').DataTable({
        "columns": [
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
                "data": "address"
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
        "autoWidth": false,
        "paging": true,
        "info": true,
        "columnDefs": [
            {"orderable": false, "targets": [2, 3, 4]}
            , {"width": "20%", "targets": [0, 1]}
            , {"width": "5%", "targets": [3, 4]}
        ]
    });

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