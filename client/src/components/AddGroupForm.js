import React, { Component, PropTypes } from 'react';

class AddGroupForm extends Component {
  render() {
    return (
      <form onSubmit={this.handleSubmit.bind(this)}>
        <input type="text" ref="title" placeholder="Title" />
        <input type="text" ref="tags" placeholder="Tags" />
        <button type="submit">Add</button>
      </form>
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
