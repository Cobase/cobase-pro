import React, { Component, PropTypes } from 'react';

export default class AddGroupForm extends Component {
  constructor(props) {
    super(props);

    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleSubmit(e) {
    e.preventDefault();
    this.props.onAddGroup(
      this.refs.title.value,
      this.refs.tags.value
    );
  }

  render() {
    return (
      <div>
        <h3>Enter required content for new group</h3>
        <form onSubmit={this.handleSubmit}>
          <label>Title:</label>
          <input type="text" ref="title" />
          <label>Twitter hashtags:</label>
          <input type="text" ref="tags" />
          <button type="submit">Create Group</button>
        </form>
      </div>
    );
  }
}

AddGroupForm.propTypes = {
  onAddGroup: PropTypes.func.isRequired,
};
