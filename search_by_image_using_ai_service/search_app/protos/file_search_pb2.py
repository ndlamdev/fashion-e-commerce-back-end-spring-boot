# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# NO CHECKED-IN PROTOBUF GENCODE
# source: file_search.proto
# Protobuf Python Version: 5.29.0
"""Generated protocol buffer code."""
from google.protobuf import descriptor as _descriptor
from google.protobuf import descriptor_pool as _descriptor_pool
from google.protobuf import runtime_version as _runtime_version
from google.protobuf import symbol_database as _symbol_database
from google.protobuf.internal import builder as _builder
_runtime_version.ValidateProtobufRuntimeVersion(
    _runtime_version.Domain.PUBLIC,
    5,
    29,
    0,
    '',
    'file_search.proto'
)
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\x11\x66ile_search.proto\"0\n\rSearchRequest\x12\x11\n\tfile_name\x18\x01 \x01(\t\x12\x0c\n\x04\x64\x61ta\x18\x02 \x03(\x0c\"\x1d\n\x0eSearchResponse\x12\x0b\n\x03ids\x18\x01 \x03(\t2>\n\x11\x46ileSearchService\x12)\n\x06Search\x12\x0e.SearchRequest\x1a\x0f.SearchResponseb\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'file_search_pb2', _globals)
if not _descriptor._USE_C_DESCRIPTORS:
  DESCRIPTOR._loaded_options = None
  _globals['_SEARCHREQUEST']._serialized_start=21
  _globals['_SEARCHREQUEST']._serialized_end=69
  _globals['_SEARCHRESPONSE']._serialized_start=71
  _globals['_SEARCHRESPONSE']._serialized_end=100
  _globals['_FILESEARCHSERVICE']._serialized_start=102
  _globals['_FILESEARCHSERVICE']._serialized_end=164
# @@protoc_insertion_point(module_scope)
