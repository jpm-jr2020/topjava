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
    var input = $(this);
    var tr = input.parents('tr');
    var id = tr.attr("id");
    var newActive = $(this).prop("checked");

    input.prop("checked", !newActive);

    $.ajax({
        url: context.ajaxUrl + id + "/" + newActive,
        type: "PUT"
    }).done(function () {
        tr.attr("data-userEnabled", newActive);
        input.prop("checked", newActive);
        successNoty(newActive ? "Enabled" : "Disabled");
    });
}