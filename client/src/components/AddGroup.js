import React, { Component } from 'react';
import AddGroupForm from './AddGroupForm';

export default class AddGroup extends Component {
  render() {
    return (
      <div>
        <p>Add group</p>

        <AddGroupForm onAddGroup={this.props.onAddGroup} />
      </div>
    );
  }
}
