// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ajax/admin/users/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            })
        }
    );

    $(".toggle").click(toggleActive);
});

function toggleActive() {
    var id = $(this).parents('tr').attr("id");
    var newActive = $(this).prop("checked");
    $.ajax({
        url: context.ajaxUrl + id + "/" + newActive,
        type: "PUT"
    }).done(function () {
        updateTable();
        successNoty("Updated");
    });
}