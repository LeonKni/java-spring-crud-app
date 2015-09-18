//TODO - fixed thead
/**
 *Fixed Headers.
 */
FixedHeader.$inject = ['$timeout'];
function FixedHeader($timeout) {
    return {
        restrict: 'A',
        link: link
    };

    function link($scope, $elem, $attrs, $ctrl) {
        var elem = $elem[0];

        // wait for data to load and then transform the table
        $scope.$watch(tableDataLoaded, function (isTableDataLoaded) {
            if (isTableDataLoaded) {
                transformTable();
            }
        });

        function tableDataLoaded() {
            // first cell in the tbody exists when data is loaded but doesn't have a width
            // until after the table is transformed
            var firstCell = elem.querySelector('tbody tr:first-child td:first-child');
            return firstCell && !firstCell.style.width;
        }

        function transformTable() {
            // wrap in $timeout to give table a chance to finish rendering
            $timeout(function () {
                // set widths of columns
                $elem.floatThead({
                    scrollContainer: function ($table) {
                        return $table.closest('#tableContainer');
                    }
                });
            });
        }
    }
}