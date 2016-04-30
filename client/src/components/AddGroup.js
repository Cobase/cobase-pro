import React, { PropTypes } from 'react';
import Topbar from './Topbar';
import AddGroupForm from './AddGroupForm';

const AddGroup = ({ onAddGroup }) => (
  <div className="main">
    <Topbar title="Add Group" />

    <div className="add-group-container">
      <section className="add-group">
        <AddGroupForm onAddGroup={onAddGroup} />
      </section>
    </div>
  </div>
);

AddGroup.propTypes = {
  onAddGroup: PropTypes.func.isRequired,
};

export default AddGroup;
