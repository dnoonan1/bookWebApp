(function(window, $) {
    
    var authorsBaseUrl = "https://localhost:8181/bookWebApp/api/v1/authors";
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
            url: authorsBaseUrl,
            success: buildAuthorTable,
            error: function(jqXHR, textStatus, errorThrown) {
                window.alert('Could not get author list due to: '
                        + errorThrown.toString());
            }
        });
        return false;
    }
    
    function buildAuthorTable(data) {
        $authorTableBody.empty();
        $.each(
            data._embedded.authors,
            function(index, author) {
                var id = author.id;
                var row = '<tr><td class="check">'
                            + '<input form="action-delete-selected" type="checkbox" name="id"'
                            + 'value="' + id + '"></td>'
                        /*+ '</td><td>' + id*/ // HATEOAS doesn't send ID
                        + '</td><td>' + author.authorName
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


