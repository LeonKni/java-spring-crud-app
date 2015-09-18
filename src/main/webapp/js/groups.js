/**
 * @author Leon K
 */
(function () {
    'use strict';

    angular.module('UnityApp')
        .controller('GroupsCtrl', GroupsCtrl);

    /**
     * Groups Controller
     */
    GroupsCtrl.$inject = ['$http'];
    function GroupsCtrl($http) {
        var model = this;
        model.title = 'Groups';
        model.addRowGroups = addRowGroups;
        model.groups = getGroups();
        model.deleteRow = deleteRow;
        model.save = save;


//-----------------------------------------Helper methods---------------------------------------------//
        /**
         * Get all groups.
         */
        function getGroups() {
            return $http.get("/settings/groups")
                .success(function (data) {
                    model.groups = data;
                })
        }

        /**
         * Add a new row to Groups table.
         */
        function addRowGroups() {
            var rowLength;
            $('#groupsTable').find('tbody')
                .append('<tr>' +
                '<td><input name="checkbox" type="checkbox"/></td>' +
                '<td><input name="groupName" class="form-control" type="text"/></td>' +
                '</tr>');
            rowLength = $('#groupsTable').find('tbody tr').length;
            $('#groupsTable').find('tbody tr input[name="groupName"]')[rowLength - 1].focus();
        }


        /**
         * Delete a row.
         */
        function deleteRow() {
            var keyList = [];
            var rowIndex = '';
            var tableIdSelector = '#groupsTable';
            //Create list of selected rows
            $(tableIdSelector).find('tbody tr input:checked').each(function () {
                rowIndex = $(this).closest('tr')[0].rowIndex - 1;
                var groupName = $(this).closest('tr').find('input[name="groupName"]')[0].value;
                if (groupName != '') {
                    keyList.push(groupName);
                }
                //Remove the table row
                $(tableIdSelector).find('tbody tr')[rowIndex].remove();
                console.log(keyList);
            });
            //API DELETE
            var request = {
                method: 'DELETE',
                url: '/settings/groups',
                headers: {
                    'Content-Type': 'application/json'
                },
                data: keyList
            };
            $http(request)
                .then(function (response) {
                    //Success message
                })
                .then(function (error) {

                });
        }

        /**
         * Save changes.
         */
        function save() {
            var data = [];
            var closeBtn = document.createElement('a');
            closeBtn.setAttribute('class', 'close');
            closeBtn.setAttribute('aria-label', 'close');
            closeBtn.innerHTML = '&times';
            var tableIdSelector = '#groupsTable';
            $(tableIdSelector).find('tbody tr').each(function () {
                //only save newly added rows
                if (!this.children[1].children[0].disabled) {
                    data.push(
                        {
                            name: $(this).find('input[name="groupName"]')[0].value
                        })
                }
            });
            //API
            $http.put("/settings/groups", data)
                .then(function (response) {
                    //success message
                    var alert = $('#alert-groups-success');
                    alert[0].textContent = 'Successfully saved changes.';
                    alert[0].appendChild(closeBtn);
                    alert.show().delay(3000).fadeOut();
                }, function (error) {
                    //error message
                    var alert = $('#alert-groups-error');
                    //409 - HTTP Conflict
                    if (error.status === 409) {
                        alert[0].textContent = 'ERROR - This group name already exists.';
                    } else {
                        alert[0].textContent = 'ERROR - ' + error.statusText;
                    }
                    alert[0].appendChild(closeBtn);
                    alert.show();
                });

            //Disable input field on save
            $(tableIdSelector).find('input[name="groupName"]').each(function () {
                if (!this.disabled) {
                    this.disabled = true;
                }
            })
        }

        /**
         * Enable toggling of Bootstrap alert
         * (By default, Bootstrap removes alert div on close)
         */
        $(function () {
            $("[data-hide]").on("click", function () {
                $(this).closest("." + $(this).attr("data-hide")).hide();
            });
        });
    }
})();