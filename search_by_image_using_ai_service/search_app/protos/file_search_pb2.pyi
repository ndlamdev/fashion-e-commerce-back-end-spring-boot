from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from typing import ClassVar as _ClassVar, Optional as _Optional

DESCRIPTOR: _descriptor.FileDescriptor


class SearchRequest(_message.Message):
    __slots__ = ("file_name", "data",)
    NAME_FIELD_NUMBER: _ClassVar[int]
    file_name: str
    data: list
    def __init__(self, file_name: _Optional[str] = ..., content: _Optional[list] = ...) -> None: ...


class SearchResponse(_message.Message):
    __slots__ = ("ids",)
    MESSAGE_FIELD_NUMBER: _ClassVar[int]
    ids: list[str]

    def __init__(self, ids: _Optional[list[str]] = ...) -> None: ...
