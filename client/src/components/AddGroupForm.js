import React, { Component, PropTypes } from 'react';

class AddGroupForm extends Component {
  render() {
    return (
      <div>
        <h3>Enter required content for new group</h3>
        <form onSubmit={this.handleSubmit.bind(this)}>
          <label>Title:</label>
          <input type="text" ref="title" />
          <label>Twitter hashtags:</label>
          <input type="text" ref="tags" />
          <button type="submit">Create Group</button>
        </form>
      </div>
    );
  }

  handleSubmit(e) {
    e.preventDefault();

    this.props.onAddGroup(
      this.refs.title.value,
      this.refs.tags.value
    );
  }
}

AddGroupForm.propTypes = {
  onAddGroup: PropTypes.func.isRequired
};

export default AddGroupForm;
