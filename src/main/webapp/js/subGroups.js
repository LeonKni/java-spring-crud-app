/**
 * @author Leon K
 */
(function () {
    'use strict';

    angular.module('UnityApp')
        .controller('SubGroupsCtrl', SubGroupsCtrl);

    /**
     * Sub-Groups Controller
     */
    SubGroupsCtrl.$inject = ['$http'];
    function SubGroupsCtrl($http) {
        var model = this;
        model.title = 'Sub Groups';
        model.groups = getAllGroups();
        model.subGroups = getAllSubGroups();
        model.addRowSubGroups = addRowSubGroups;
        model.deleteRow = deleteRow;
        model.save = save;


//-----------------------------------------Helper methods---------------------------------------------//
        /**
         * Get all sub-groups.
         */
        function getAllSubGroups() {
            return $http.get("/settings/subGroups")
                .success(function (data) {
                    model.subGroups = data;
                    //Pre-select dropdown options based on setting
                    setTimeout(function () {
                        for (var i = 0; i < data.length; i++) {
                            //pre-select group
                            for (var j = 0; j < $('#subGroupsTable select[name="groupName"]')[i].children.length; j++) {
                                if (data[i].groupName != null &&
                                    $('#subGroupsTable select[name="groupName"]')[i].children[j].label === data[i].groupName) {
                                    $('#subGroupsTable select[name="groupName"]')[i].selectedIndex
                                        = $('#subGroupsTable select[name="groupName"]')[i].children[j].index;
                                }
                            }
                        }
                    }, 100);
                })
        }

        /**
         * Get all groups.
         */
        function getAllGroups() {
            return $http.get("/settings/groups")
                .success(function (data) {
                    model.groups = data;
                })
        }

        /**
         * Add a new row to SubGroups table.
         */
        function addRowSubGroups() {
            var select = angular.element('<select name="groupName" class="form-control" data-ng-model="selectedGroup" ' +
                'data-ng-options="group.name for group in subGroupCtrl.groups">');
            var defaultOption = document.createElement('option');
            var option;
            var groups = model.groups;
            defaultOption.value = '';
            defaultOption.innerHTML = '-Select-';
            select.append(defaultOption);
            for (var i = 0; i < groups.length; i++) {
                option = document.createElement('option');
                option.value = groups[i].name;
                option.innerHTML = groups[i].name;
                select.append(option);
            }

            var rowLength;
            $('#subGroupsTable').find('tbody')
                .append('<tr data-ng-controller="SubGroupsCtrl">' +
                '<td><input name="checkbox" type="checkbox"/></td>' +
                '<td><input name="subGroupName" class="form-control" type="text"/></td>' +
                '<td>' + select[0].outerHTML + '</td>' +
                '</tr>');
            rowLength = $('#subGroupsTable').find('tbody tr').length;
            $('#subGroupsTable').find('tbody tr input[name="subGroupName"]')[rowLength - 1].focus();
        }


        /**
         * Delete a row.
         */
        function deleteRow() {
            var keyList = [];
            var rowIndex = '';
            var tableIdSelector = '#subGroupsTable';
            //Create list of selected rows
            $(tableIdSelector).find('tbody tr input:checked').each(function () {
                rowIndex = $(this).closest('tr')[0].rowIndex - 1;
                var key = $(this).closest('tr').find('input[name="subGroupName"]')[0].value;
                if (key != '') {
                    keyList.push(key);
                }
                //Remove the table row
                $(tableIdSelector).find('tbody tr')[rowIndex].remove();
                console.log(keyList);
            });
            //API DELETE
            var request = {
                method: 'DELETE',
                url: '/settings/subGroups',
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
            var tableIdSelector = '#subGroupsTable';
            $(tableIdSelector).find('tbody tr').each(function () {
                //only save newly added rows
                if (!this.children[1].children[0].disabled) {
                    data.push(
                        {
                            name: $(this).find('input[name="subGroupName"]')[0].value,
                            groupName: $(this).find('select[name="groupName"]')[0].selectedOptions[0].textContent
                        })
                }
            });
            //API
            $http.put("/settings/subGroups", data)
                .then(function (response) {
                    //success message
                    var alert = $('#alert-sub-groups-success');
                    alert[0].textContent = 'Successfully saved changes.';
                    alert[0].appendChild(closeBtn);
                    alert.show().delay(3000).fadeOut();
                }, function (error) {
                    //error message
                    var alert = $('#alert-sub-groups-error');
                    //409 - HTTP Conflict
                    if (error.status === 409) {
                        alert[0].textContent = 'ERROR - This sub-group name already exists.';
                    } else {
                        alert[0].textContent = 'ERROR - ' + error.statusText;
                    }
                    alert[0].appendChild(closeBtn);
                    alert.show();
                });

            //Disable input field on save
            $(tableIdSelector).find('input[name="subGroupName"]').each(function () {
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