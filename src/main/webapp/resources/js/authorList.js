(function(window, $) {
    
    var authorController = "author";
    var $authorTableBody = $('.authorList tbody');
    
    $(function() {
        if ($authorTableBody.length) {
            refreshAuthors();
            $('#refresh').submit(refreshAuthors);
        }
    });
    
    function refreshAuthors() {
        $.ajax({
            type: 'GET',
            url: authorController + '?action=listAjax',
            success: buildAuthorTable,
            error: function(jqXHR, textStatus, errorThrown) {
                window.alert('Could not get author list due to: '
                        + errorThrown.toString());
            }
        });
        return false;
    }
    
    function buildAuthorTable(authors) {
        $authorTableBody.empty();
        $.each(
            authors,
            function(index, author) {
                var id = author.id;
                var row = '<tr><td class="check">'
                            + '<input form="action-delete-selected" type="checkbox" name="id"'
                            + 'value="' + id + '"></td>'
                        + '</td><td>' + id
                        + '</td><td>' + author.name
                        + '</td><td>' + author.dateAdded
                        + '<td><button form="edit-' + id + '">'
                        +        '<span class="glyphicon glyphicon-edit" title="Edit"></span>'
                        +    '</button>'
                        +    '<button form="delete-' + id + '">'
                        +        '<span class="glyphicon glyphicon-trash" title="Delete"></span>'
                        +    '</button>'
                        + '</td></tr>';
                $authorTableBody.append(row);
            }
        );
    }
    
})(window, jQuery);


